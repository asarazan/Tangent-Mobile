# Tangent Mobile — 2023 → 2026 Modernization Analysis

*Prepared August 2026. Research verified against official sources (Kotlin/JetBrains docs, Android
Developers, GitHub release pages/changelogs, Touchlab SKIE docs) via live web search — not from
training-data recall of version numbers, which is stale for this repo's 2022-era pins. Every
claim below is either cited or explicitly flagged as unverified/secondary-sourced. Where research
could not confirm something (dates, some edge-case behaviors), that is stated rather than guessed.*

## Executive summary

Every pin in `buildSrc/src/main/kotlin/Deps.kt` is now 2–7 major versions behind current stable,
and the toolchain has crossed several hard version floors since 2023: **Gradle 9 will not run
Kotlin below 2.0.0 or AGP below 8.4.0 at all**, and **Ktorfit's current stable release requires
Kotlin ≥2.2.0 and Ktor ≥3.5.0**. This means the historically independent "bump Kotlin," "bump
Gradle," "bump Ktor," and "bump Ktorfit" upgrades have become one coupled move — there is no
incremental path through the middle of this dependency graph. Separately, **Google Play's API-36
targeting deadline for app updates is August 31, 2026** — effectively immediate — which makes the
compileSdk/targetSdk bump the most time-sensitive item in this document, independent of the
Kotlin/Gradle work.

Beyond version numbers, three structural changes matter most for this codebase specifically:
(1) Kotlin's **Default Hierarchy Template**, standard since 1.9.20, directly conflicts with the
repo's hand-rolled `iosMain.dependsOn(commonMain)` wiring in `shared/build.gradle.kts` and must be
deleted, not layered on top of; (2) **kotlinx-datetime 0.7+ removed `Instant`/`Clock`** in favor of
the Kotlin stdlib's `kotlin.time.Instant`, which is a real code change to
`shared/.../dates/DateFormatting.kt`'s `expect`/`actual` pair, not just a version bump; (3)
**SQLDelight 2.x's `asFlow()` requires an explicit dispatcher** on `mapToList`, which changes the
shape of the reactive query in `TimelineStorage.kt`, though the on-disk SQLite format and existing
`profile_${id}.db` files are unaffected. Stored JSON blobs (`MastodonJson`, `ServerCredentials`,
cached `Status` rows) are, per the serialization-library changelog review, safe across the
kotlinx.serialization upgrade — no wire-format-breaking changes were found between 1.4.1 and the
current stable line.

On iOS, the repo is still the unmodified KMP wizard stub. That's an opportunity: the modernization
work and the *first real iOS feature work* can be done together, using current tooling from day
one (SKIE, direct Xcode integration, `@Observable`, Swift 6 mode) rather than building the stub
out on 2022-era plumbing and re-modernizing it immediately after.

---

## 1. Kotlin 1.7.20 → 2.x

**Current stable: Kotlin 2.4.0** (released August 11, 2026; a 2.4.20-RC followed the next day,
with 2.4.20 stable planned for September 2026).
[What's new in Kotlin 2.4.0](https://kotlinlang.org/docs/whatsnew24.html)

- **K2 compiler**: default and Stable since Kotlin 2.0.0 (JVM/Native/Wasm/JS). As of **2.4.0, K1
  support has been fully removed** — `-language-version=1.9` is no longer accepted at all. This is
  a one-way door: there is no "land on 2.0 and keep K1 as a fallback" option once you're on 2.4.
- **KMP stability**: core Kotlin Multiplatform reached **Stable in 1.9.20**. Android, iOS,
  Desktop(JVM), Server(JVM), and Kotlin/JS-web targets are Stable; Wasm-web, watchOS, and tvOS
  remain Beta — not relevant to this repo's iOS targets (iosX64/iosArm64/iosSimulatorArm64), which
  are all Stable.
- **Default Hierarchy Template (DHT)** — **direct conflict with this repo**: experimental in
  1.8.20, default since 1.9.20. `shared/build.gradle.kts` currently hand-wires:
  ```kotlin
  val iosMain by creating {
      dependsOn(commonMain)
      iosX64Main.dependsOn(this)
      iosArm64Main.dependsOn(this)
      iosSimulatorArm64Main.dependsOn(this)
  }
  ```
  This is *exactly* the shape DHT creates automatically. Gradle detects the existing explicit
  `dependsOn` edges and silently skips applying DHT rather than erroring — but KSP tooling that
  assumes DHT's source-set names/shape can misbehave (see
  [google/ksp#1569](https://github.com/google/ksp/issues/1569)). **Migration: delete the manual
  `iosMain`/`iosTest` creation blocks entirely** and let DHT synthesize them; same applies to the
  `iosTest` block later in the same file.
- **Kotlin/Native memory manager**: the new MM has been default since 1.7.20, so this repo was
  already on it — no migration needed. The legacy MM was fully removed in 1.9.20. Kotlin 2.4.0
  additionally **enables the CMS garbage collector by default** (revertible via
  `kotlin.native.binary.gc=pmcs` if regressions surface).
- **expect/actual classes**: still **Beta** as of 2.4.0. The `-Xexpect-actual-classes` compiler
  flag is still needed to suppress warnings — relevant to `expect class DriverFactory` in
  `shared/src/{commonMain,androidMain,iosMain}/kotlin/social/tangent/mobile/data/SqlDelight.kt`.
  Not a blocker, just a persistent warning to keep suppressing.
- **Compose compiler bundling**: since Kotlin 2.0.0, the Compose compiler ships *with* the Kotlin
  Gradle plugin as `org.jetbrains.kotlin.plugin.compose` (version = Kotlin version, no separate
  pin). The current `Versions.kotlinCompilerExt = "1.3.2"` model in `Deps.kt`, wired as a
  standalone `composeOptions { kotlinCompilerExtensionVersion = ... }`, is obsolete under 2.x —
  apply the new plugin per Compose-consuming module and delete the old option.

**Effort: L.** Seven minor-version jump, K2 rewrite semantics, DHT structural removal, and Compose
compiler decoupling all land at once, with no K1 fallback if something breaks mid-way.

## 2. KSP → KSP2

- Ktorfit gained KSP2 support in **v2.4.0** (Feb 2025); current Ktorfit stable is **2.7.5** (see
  §6), which requires **Kotlin ≥2.2.0, KSP ≥2.0.2, Ktor ≥3.5.0**.
- A historical cross-module KSP1 bug affecting Ktorfit on Kotlin 2.0.x
  ([Foso/Ktorfit#634](https://github.com/Foso/Ktorfit/issues/634), `Error type not resolvable`
  across modules) has no confirmed closure in the changelog research pulled here — **treat as
  unresolved until smoke-tested**, not as fixed.
- SQLDelight 2.x uses Gradle-native (non-KSP) codegen, so it sits outside the KSP2 compatibility
  surface entirely — no interaction to track there.
- KSP2 introduces new default annotation use-site rules aligned with the Analysis API (KEEP 402);
  broadly compatible with this repo's simple `@GET`/`@POST`/etc. annotation usage in `Api.kt`.

**Effort: M.** Bump alongside Kotlin 2.4/KSP2 as one step, then explicitly smoke-test the
shared↔androidApp KSP codegen boundary (the `kspCommonMainMetadata`/`kspAndroid`/`kspIosX64`/
`kspIosSimulatorArm64` targets in `shared/build.gradle.kts`) rather than assuming it's clean.

## 3. Gradle 8.9 → 9.x

**Current stable: Gradle 9.7.0.**

- **JDK**: Gradle 9's daemon requires JVM 17+ — already satisfied (repo pins JDK 17).
- **Hard floors that gate this repo specifically: Gradle 9 requires KGP ≥2.0.0 and AGP ≥8.4.0.**
  At today's pins (Kotlin 1.7.20, AGP 7.4.0), **the build will not run under Gradle 9 at all** —
  this is not a "nice to modernize eventually" item, it's a wall. Gradle 9 cannot land before, or
  independently of, the Kotlin 2.x + AGP 8.4+ move.
- Kotlin DSL build scripts under Gradle 9 compile against Kotlin 2.2 semantics (no
  `this@Build_gradle`-style labels, stricter generics/nullability) — touches every
  `build.gradle.kts` and `buildSrc` file in the repo, though mechanically.
- Groovy bumped to 4.0 — low impact; this repo's build files are pure Kotlin DSL.
- Gradle's public API nullability annotations moved to JSpecify — possible stricter null-checking
  surfaced in `buildSrc/src/main/kotlin/Instances.kt` if it touches Gradle APIs directly.
- `jcenter()` repository support is removed — worth a one-line confirmation that no build file
  still references it (unlikely, but cheap to check).
- No CocoaPods/Kotlin-Native-specific breakage was found in the Gradle 9 upgrade notes; the native
  build-tooling changes documented there concern Gradle's own C++/Swift plugins, not
  Kotlin/Native's cocoapods integration.

**Effort: M–L, and strictly sequenced last** — Gradle 9 is the domino that falls only after Kotlin
2.x and AGP 8.4+ are both in place.

## 4. AGP 7.4.0 → current

**Current stable: AGP 9.3.0** (Aug 13, 2026), which itself requires **Gradle ≥9.5.0** — reinforcing
that Kotlin/AGP/Gradle move as one coordinated stage, not three sequential ones.

- AGP 8.x+ requires JDK 17 — already satisfied.
- compileSdk/AGP floor table found during research: API 33 needs AGP ≥7.2 (current pin already
  clears this trivially); API 35 needs AGP ≥8.6.0; API 36.1 needs AGP ≥8.13.0; API 37 needs AGP
  ≥9.1.1. **compileSdk and AGP must rise together** — you cannot bump one without the other past a
  certain point.
- `namespace` is already declared in `shared/build.gradle.kts` (`namespace =
  "social.tangent.mobile"`) — the AGP 7→8 namespace requirement is already satisfied, no action
  needed there.
- `desugar_jdk_libs` pin (`2.0.0`, currently commented out in `Deps.kt`'s consumer) — latest is
  `2.1.5` if/when desugaring is re-enabled.
- Google publishes an official "AGP 9 Upgrade" Android Studio skill
  (`android skills add --skill agp-9-upgrade`) aimed at exactly this class of multi-major jump —
  worth using as an assist during the actual migration PR, not just for research.

**Effort: L.** Five major AGP versions, compileSdk forced to move in lockstep, and entangled with
both the Kotlin 2.x move and the Gradle 9 move — this is the heaviest single piece of Stage 1.

## 5. compileSdk / targetSdk 33 → current Play requirement

- **Google Play policy: new apps and updates to existing apps must target API 36 (Android 16) by
  August 31, 2026**, with a one-time extension available to November 1, 2026 on request. Existing
  installed apps need at least API 35 to remain visible to new users on Play.
- **Latest shipped Android platform: Android 17 ("Cinnamon Bun"), API 37**, stable since June 16,
  2026.
- Tangent's compileSdk 33 is four platform levels behind, and **the Play deadline falls this
  month** relative to the "current date" this research was run against — this is the single most
  time-sensitive item in the whole document, independent of anything else in the Kotlin/Gradle
  chain.
- **Recommendation: target API 36 at minimum immediately; API 37 if the AGP 9.x bump (§4) happens
  in the same pass**, since AGP 9.x is required to compile against API 37 anyway.
- **minSdk 26**: current Android API-level distribution percentages could not be verified from a
  primary source during this research pass — **do not cite a specific percentage**. No library in
  this dependency set currently forces a minSdk floor above 26. Recommendation: leave minSdk at 26
  unless there's an independent simplification motive (e.g., dropping pre-Oreo-specific code
  paths); this is a judgment call, not a forced move.

**Effort: S–M for the compileSdk/targetSdk bump itself, but time-sensitive** given the Play
deadline. Behavioral changes introduced by API 34–37 (predictive back gesture, runtime permission
changes, foreground service types) need real device/emulator testing, not just a successful build.

## 6. buildSrc `Deps.kt` vs Gradle version catalogs (`libs.versions.toml`)

- The official Android/Kotlin ecosystem direction is toward `gradle/libs.versions.toml` — Android
  Studio ships a dedicated migration assistant specifically for buildSrc-object-style dependency
  management like this repo's `Deps.kt`.
- **Agent-SDLC-specific reasoning** (this repo's stated mission per `AGENTS.md`): a TOML catalog is
  declarative data, not compiled Kotlin — an agent patching a version number in `libs.versions.toml`
  cannot break the *build script's own compilation* the way a typo in `Deps.kt` can (a broken
  `buildSrc` module fails before any other Gradle task even starts, including error reporting for
  the fix). Renovate/Dependabot and Android Studio both have first-class structured support for
  version-catalog bump PRs; matching that against arbitrary Kotlin `object` declarations requires
  bespoke tooling. A fixed, versioned schema is a better interface for an LLM agent to reliably
  read and write than free-form Kotlin.
- Counter-consideration: `Deps.kt` currently does more than list versions — it composes strings
  (`"${Versions.koin}"` reused across `koinCore`/`koinTest`/etc.) and coexists with
  `buildSrc/Instances.kt`'s separate manifest-codegen logic, which has no catalog equivalent and
  isn't part of this migration regardless.
- **Recommendation: migrate to `libs.versions.toml`**, specifically because of the agent-driven-SDLC
  mission stated in `AGENTS.md`, done in the same PR as the Stage 1 version bumps (§7 below) so the
  catalog is populated with already-current versions rather than migrated twice.

**Effort: M.** Touches every module's `build.gradle.kts` dependency declarations; mechanical but
repo-wide.

## 7. CI hygiene (`.github/workflows/ci.yml`)

| Action | Current pin | Latest (Aug 2026) | Notes |
|---|---|---|---|
| `actions/setup-java` | v4 | **v5** (v5.2.0) | v1–v4 are deprecated; v6 is in development. `jdkFile` input renamed to `jdk-file` (old name kept as an alias). |
| `actions/checkout` | v4 | **v7.0.1** | v5+ changed the default safety behavior for `pull_request_target` checkouts (`allow-unsafe-pr-checkout`) — not used by this repo's triggers, but worth reviewing on bump. |
| `actions/cache` | v4 | **v6.1.0** | v5 moved to the Node 24 runtime (needs Actions Runner ≥2.327.1, satisfied by GitHub-hosted runners); v6 is an ESM rewrite. |
| `actions/upload-artifact` | v4 | **v7.0.1** | v6 on Node 24; v7 adds an ESM rewrite plus an `archive` param. |
| `gradle/actions/setup-gradle` | v4 | **v6.3.0** (Aug 2, 2026) | The most stale pin proportionally; v6 changed build-caching-component licensing terms — worth a one-line read before bumping. |

Common thread: every current major requires the Node 24 Actions runtime, which GitHub-hosted
runners already provide — no infra change needed on this repo's side, just the version bumps.

**Effort: S.** Pure version bumps; validate CI still passes after, since checkout-safety and
runtime-version defaults did shift underneath the pins.

### Unverifiable / explicitly flagged from Stage-0/1 research
- Android API-level distribution percentages for the minSdk decision — no primary source found;
  not cited above, and should not be cited without one.
- `Foso/Ktorfit#634` closure status — unconfirmed against the primary issue tracker; treat as open
  until smoke-tested during the actual upgrade.

---

## 8. SQLDelight 1.5.4 → 2.x

**Current stable: 2.3.2.** (Multiple sources cross-checked; the exact release *dates* returned
were inconsistent between sources, so only the version number is asserted here, not a date.)

- **Gradle plugin coordinate change**: `com.squareup.sqldelight` → **`app.cash.sqldelight`**
  (confirmed via the official SQLDelight upgrading-to-2.0 guide and changelog).
- **Import path changes** (all in `shared/src/{androidMain,iosMain}/kotlin/social/tangent/mobile/data/SqlDelight.kt`):
  - `com.squareup.sqldelight.android.AndroidSqliteDriver` → `app.cash.sqldelight.driver.android.AndroidSqliteDriver`
  - `com.squareup.sqldelight.drivers.native.NativeSqliteDriver` → `app.cash.sqldelight.driver.native.NativeSqliteDriver`
  - `com.squareup.sqldelight.db.SqlDriver` → `app.cash.sqldelight.db.SqlDriver`
  - Coroutines extension artifact becomes `app.cash.sqldelight:coroutines-extensions` (confirmed
    multiplatform).
- **Gradle DSL change** in `shared/build.gradle.kts`:
  ```kotlin
  // current (1.5.4)
  sqldelight {
      database("TangentDatabase") { packageName = "social.tangent.mobile" }
  }
  // 2.x
  sqldelight {
      databases {
          create("TangentDatabase") { packageName.set("social.tangent.mobile") }
      }
  }
  ```
  (Property-based DSL now; `sourceFolders` was also renamed to `srcDirs` if this repo ever adds
  custom source folders, which it currently doesn't.)
- **Real code-shape break, not just a rename — directly hits `TimelineStorage.kt`**: the current
  reactive-query pattern
  ```kotlin
  private val raw = db.statusQueries.selectAll(::timelineMapper)
      .asFlow()
      .map { Timeline(it.executeAsList()) }
      .stateIn(scope, SharingStarted.Eagerly, Timeline(listOf()))
  ```
  becomes, under 2.x, an explicit-dispatcher `mapToList`:
  ```kotlin
  .asFlow().mapToList(Dispatchers.IO)
  ```
  This needs an actual code change during the upgrade, not a search-and-replace on package names.
- **Data compatibility**: this is a pure Kotlin API/package migration — the on-disk SQLite file
  format is untouched, and `.sqm` migration files are independent of the SQLDelight library major
  version. Existing `profile_${id}.db` files (per-account timeline caches) will open fine after the
  upgrade with no user-facing data loss. The only thing worth grepping for before upgrading is any
  use of `AfterVersionWithDriver`/`migrateWithCallbacks` (renamed to `AfterVersion`/`migrate()` in
  2.x) — this repo does not currently use either, so that's not expected to be a factor.

**Effort: S–M.** Mostly mechanical coordinate/import renames plus two real code-shape changes
(the DSL block and the `asFlow`/`mapToList` pattern). No data-migration risk to the cached
timeline.

## 9. Ktor 2.1.3 → 3.x (client)

**Current stable: Ktor 3.5.1**, which requires **Kotlin 2.0+** (already implied by Stage 1).

- The official 2.x→3.0 migration guide is almost entirely server-module-focused; this repo has no
  Ktor server usage (`shared/.../api/Ktorfit.kt` is a pure client setup), so most of that guide
  doesn't apply.
- The client code exactly as written today —
  ```kotlin
  install(Logging) { logger = Logger.DEFAULT; level = LogLevel.ALL; logger = object : Logger {...} }
  install(ContentNegotiation) { json(defaultJson) }
  ```
  — has **no breaking API change** found against current Ktor 3.5.x client plugin APIs.
- Confirmed client-relevant changes elsewhere in the API surface (not currently used by this repo,
  but worth knowing if the client layer grows): `HttpResponse.content` renamed to `rawContent`;
  `SocketTimeoutException` is now a typealias of the `java.net` exception (matters only if code
  catches it by type, which this repo doesn't); internal I/O moved to `kotlinx-io` (invisible at
  the call-site level).
- **Lower-confidence / secondary-sourced findings, flagged explicitly as "watch for" rather than
  confirmed**: community reports of Darwin-engine issues in Ktor 3.x (a `handleChallenge` memory
  leak, certificate-pinning validation quirks, a `close()` race, WebSocket `maxFrameSize`
  handling). None of these were verified against Ktor's primary issue tracker in this research
  pass, and none affect this repo's current plain-JSON-REST usage pattern — but they're worth a
  search immediately before the iOS Darwin-engine upgrade specifically, given this repo is about
  to start real iOS networking for the first time.
- OkHttp engine (Android): no breaking changes surfaced for this repo's usage.

**Effort: S** for this repo's simple REST/JSON client usage, once Kotlin 2.x is already in place.

## 10. Ktorfit 1.0.0-beta16 → current stable

**Current stable: Ktorfit 2.7.5** (~June 2026). Ktorfit left beta and reached its 1.0.0 release on
March 2, 2023 — this repo has been pinned to a pre-1.0 beta for the library's entire stable
lifetime.

- **Hard version-coupling requirement, confirmed in the Ktorfit changelog: Ktorfit 2.7.5 requires
  Kotlin ≥2.2.0, KSP ≥2.0.2, and Ktor ≥3.5.0.** This is the concrete mechanism that forces Kotlin,
  KSP, and Ktor to move together with Ktorfit rather than independently — Ktorfit cannot be bumped
  in isolation on top of the current 1.7.20/KSP-1.7.20-1.0.7/Ktor-2.1.3 stack.
- KSP2 support landed in Ktorfit 2.4.0; a KSP2 + KMP-resources circular-dependency bug was fixed in
  2.4.1 (not relevant to this repo, which doesn't use KMP resources, but confirms KSP2 stability
  matured by that point).
- The generated `_ApiImpl.kt` "internal API" warning this repo currently sees: whether it's
  resolved in current Ktorfit **could not be confirmed** from the changelog — the closest match
  found was annotation-propagation work in 2.7.1, but the exact warning text wasn't matched against
  a specific fix commit. Treat as still-present until verified against the actual warning text
  during the upgrade, not as resolved.
- The annotations this repo uses in `Api.kt` (`@GET`, `@POST`, `@Header`, `@FormUrlEncoded`,
  `@Field`, `@Path`, `@Query`) are stable across this version range — no breaking changes found.
- One known current issue worth being aware of (not applicable here, since this repo is a
  multi-target KMP project, not single-target): single-target KMP projects can fail
  `createXApi()` extension-function resolution, with the deprecated generic
  `ktorfit.create<XApi>()` syntax as the workaround.

**Effort: M** — driven entirely by the forced Kotlin 2.x + Ktor 3.x coupling; Ktorfit's own API
surface is stable enough that, isolated from that coupling, the bump would be S.

## Compatibility matrix (§8–10 cross-cutting)

- Ktorfit 2.7.5 → requires Ktor ≥3.5.0 + Kotlin ≥2.2.0 + KSP ≥2.0.2 (the binding constraint for
  Stage 1/2 sequencing).
- Ktor 3.0+ → requires Kotlin 2.0+.
- SQLDelight 2.x's minimum Kotlin version **could not be verified** in this research pass; indirect
  evidence (its Gradle-native, non-KSP codegen approach) suggests no conflict with the Kotlin 2.x
  line, but this is stated as unverified, not confirmed.
- SQLDelight, Ktor, and Ktorfit have no direct dependency on each other — the thing that forces
  them into one stage is that **all three transitively require Kotlin 2.x**, which is itself
  required by Gradle 9 and AGP 8.4+ (§3–4).

## 11. Koin 3.2.2/3.3.0 → 4.x

**Current stable: Koin 4.2.2** (June 15, 2026).

- Core `KoinComponent`/`get()`/`inject()` object-level pattern — as used throughout, e.g.
  `shared/.../sdk/storage/MastodonStorage.kt`'s `object MastodonStorage : KoinComponent { private
  val settings by lazy { get<Settings>() } }` — is stable and low-risk across this version range.
- Deprecated/superseded: the old ViewModel DSL and old Compose `viewModel()`/`getViewModel()`
  helpers were unified into `koinViewModel<T>()`. This repo doesn't use those helpers today — it
  hand-wraps shared viewmodels in `AndroidViewModel` (per `AGENTS.md`'s architecture description)
  — so this change is lower urgency here than in a typical Koin-in-Compose app.
- `koin-android-compat` and `koin-androidx-workmanager` still exist in 4.x; `koin-androidx-navigation`
  gained Navigation 3 support in Koin 4.2.
- **Requires Kotlin ≥2.0.20** — gated behind the Stage 1 Kotlin move, same as the rest of this
  section.
- Koin's own docs state that the 4.0 line is "no longer actively maintained" — target 4.2.x
  specifically, not just "4.x."

**Effort: M.**

## 12. kotlinx.serialization, kotlinx.coroutines, multiplatform-settings, kotlinx-datetime

- **kotlinx.serialization 1.4.1 → 1.11.0** (April 9, 2026). Every change found in the changelog
  between these versions is additive or opt-in (`allowTrailingComma`, `allowComments`,
  `classDiscriminatorMode`, `@JsonIgnoreUnknownKeys`, etc.) — **no wire-format-breaking entries**
  were found. This is the load-bearing finding for data compatibility: the stored `MastodonJson`
  and `ServerCredentials` blobs (`shared/.../sdk/storage/MastodonStorage.kt`,
  `shared/.../sdk/credentials/ServerCredentials.kt`) and the cached `Status` JSON columns in
  `TimelineStorage` should keep decoding correctly after the upgrade — existing users' saved login
  credentials and cached timeline are not expected to need a migration path. This is verified by
  the *absence* of breaking changelog entries across seven minor versions, not by an explicit
  "backward compatible" guarantee from JetBrains — worth a real round-trip test (serialize on old,
  deserialize on new) before shipping, given how much rides on this specific claim. Effort: **S**.
- **kotlinx.coroutines 1.6.4 → 1.11.0** (May 11, 2026). No breaking changes found to
  `StateFlow`/`SharedFlow`/`stateIn`/`Flow.map`, the exact APIs `TimelineStorage.kt` and the MVI
  `MobileViewModel` base rely on. The "new memory manager" coroutines-compatibility concern
  flagged in the original brief is moot — the new MM has been the default since Kotlin 1.7.20 (see
  §1), and coroutines has supported it since 1.6.0. Coroutines 1.10+ targets Kotlin 2.1+, so
  sequence this alongside the Stage 1 Kotlin move rather than before it. Effort: **S**.
- **multiplatform-settings 1.0.0-RC → 1.3.0** (Nov 29, 2024 — nothing newer found as of this
  research). All the breaking API changes in this library's history happened going *from* RC *to*
  1.0.0 (removed deprecated APIs, changed `Factory.create()`'s return type, dropped
  `useFrozenListeners`, dropped the legacy JS backend) — nothing further breaking through 1.3.0.
  This repo is upgrading past that one breaking boundary in a single step, so it needs the same
  care as any RC→stable jump, but there's nothing further to worry about after landing on 1.3.0.
  Effort: **S**.
- **kotlinx-datetime 0.4.0 → 0.8.0** (May 7, 2026) — **the one real code migration in this group.**
  `Instant` and `Clock` moved into the Kotlin standard library itself
  (`kotlin.time.Instant`/`kotlin.time.Clock`) as of Kotlin 2.1.20; kotlinx-datetime 0.7.0 removed
  its own `Instant`/`Clock` types entirely, and 0.7.1 re-added them as type aliases specifically to
  ease migration. This directly touches
  `shared/src/{commonMain,androidMain,iosMain}/kotlin/social/tangent/mobile/dates/DateFormatting.kt`'s
  `expect fun Instant.shortDateFormat(): String` / matching `actual fun` declarations — the import
  needs to move from `kotlinx.datetime.Instant` to the stdlib type, in lockstep across all three
  source sets, gated on Kotlin ≥2.1.20 already being in place. This is also the natural moment to
  finally implement the iOS `actual` (currently a literal `"TODO"` stub) rather than carrying that
  stub forward through another migration. Effort: **M**.

## 13. Compose Material 2 → Material 3 / Compose BOM

**Current Compose BOM: 2026.08.00** (Compose 1.12 line), which itself requires **compileSdk 37 /
AGP ≥9.1.1** — another concrete link back to the compileSdk/AGP decisions in §4–5.

- **Material 2 (`androidx.compose.material`) is still actively maintained** — latest release 1.11.4
  (July 2026), not deprecated. This means **adopting the Compose BOM while staying on Material 2 is
  a valid, lower-risk intermediate step** — you can pin an older BOM release to decouple the BOM
  adoption from the AGP 9.1.1 floor if that floor isn't reached yet in the same PR.
- A full Material 2 → Material 3 migration concretely touches, in this codebase:
  - `MyApplicationTheme.kt`: `Colors`/`darkColors()`/`lightColors()` → `ColorScheme`/
    `darkColorScheme()`/`lightColorScheme()`. The custom `Colors` extension properties
    (`onBackgroundFainter`, `onBackgroundFaint`, `url`) need re-authoring against `ColorScheme`
    since M3 doesn't support arbitrary extension colors the same way M2's flatter `Colors` class
    did.
  - `MaterialTheme.colors.*` → `MaterialTheme.colorScheme.*` call-site renames throughout
    `HomeTopBar.kt`, `HomeBottomBar.kt`, and other files under `androidApp/.../compose/` — mostly
    mechanical, but repo-wide.
  - `HomeScreen.kt`'s `Scaffold`/`rememberScaffoldState()` usage: **M3 removed `ScaffoldState`
    entirely** — snackbar handling moves to a standalone `SnackbarHostState`, and M3's `Scaffold`
    dropped all drawer-related parameters. The currently **commented-out**
    `// drawerContent = { HomeDrawer() }` line in `HomeScreen.kt` cannot simply be uncommented
    under M3 — re-enabling the drawer (if desired) requires restructuring around
    `ModalNavigationDrawer` wrapping the `Scaffold`, not passing a `drawerContent` param to it.
- **Recommendation: defer full Material 3 as a separate, later, purely cosmetic pass** — it has no
  dependency-graph coupling to any of the Stage 1–3 work above (Kotlin/Gradle/AGP/SQLDelight/Ktor/
  Ktorfit/Koin/serialization are all independent of the M2-vs-M3 decision), and the effort is real
  (component-level rewrites, not just renames) with purely visual payoff. Adopt the BOM (M2-pinned)
  in the same pass as the AGP/compileSdk work; leave the M3 rewrite as its own tracked issue.

**Effort: BOM-only adoption: S. Full Material 3 migration: L** (deferred).

## 14. iOS packaging: CocoaPods → direct integration / SPM, SKIE, Swift 6, `@Observable`

The iOS app is currently the unmodified KMP wizard stub — this is the one area of the document
where "modernize the toolchain" and "build the first real feature" are the same PR, not two.

- **KMP↔Xcode integration model**: `kotlin("native.cocoapods")` is **not formally deprecated**,
  but current JetBrains documentation steers toward **direct integration** — the
  `embedAndSignAppleFrameworkForXcode` Gradle task wired into an Xcode "Run Script" build phase —
  as the modern default, with Swift Package Manager import as a newer layer on top for consuming
  external Swift packages *into* Kotlin (not for distributing the KMP framework itself; exporting
  KMP *as* a Swift package is explicitly **not yet supported** by JetBrains tooling).
  [Direct integration docs](https://kotlinlang.org/docs/multiplatform/multiplatform-direct-integration.html) ·
  [CocoaPods overview](https://kotlinlang.org/docs/multiplatform/multiplatform-cocoapods-overview.html) ·
  [CocoaPods → SwiftPM migration guide](https://kotlinlang.org/docs/multiplatform/multiplatform-cocoapods-spm-migration.html)
  — the existence of an official migration guide *from* CocoaPods *to* SwiftPM/direct integration
  is itself a signal about which direction JetBrains considers current.
- CocoaPods integration and `embedAndSignAppleFrameworkForXcode` are **mutually exclusive** — this
  is a full swap, not an incremental toggle: remove the `cocoapods {}` block from
  `shared/build.gradle.kts`, delete the generated `.podspec`/`iosApp/Podfile`, and reconfigure the
  Xcode project with the Run Script phase instead.
- **Tangent-specific payoff**: this repo's CI (`.github/workflows/ci.yml`, iOS job) currently
  carries a documented workaround — a manual `./gradlew :shared:syncFramework
  -Pkotlin.native.cocoapods.platform=iphonesimulator ...` pre-sync step, with an inline comment
  explaining it exists to avoid a race between Xcode's explicit-module dependency scan and the
  CocoaPods "Build shared" run-script phase. **Direct integration removes this race condition
  entirely** (there's no separate CocoaPods-managed phase to race against), meaning this whole
  CI workaround block can be deleted as part of the migration, not just relocated.
- `extraSpecAttributes["libraries"] = "'c++', 'sqlite3'"` (needed because SQLDelight's native
  driver links against system sqlite3) is podspec-specific syntax; direct integration needs the
  equivalent expressed as `binaries.framework { linkerOpts += "-lsqlite3" }` (and `-lc++` similarly)
  — a config translation, not a functional blocker.
- **Recommendation: switch to direct integration + `embedAndSign`** as part of the iOS packaging
  work. **Effort: M** (Gradle config rewrite, Xcode project changes, CI simplification, sqlite3/c++
  linker-flag translation).

- **SKIE (Touchlab)**: latest release found is **~0.10.14** (July 27, 2026, per GitHub releases —
  single-sourced in this research pass, treat the exact patch number as best-effort rather than
  fully corroborated). SKIE wraps `StateFlow` as `SkieSwiftStateFlow` and `SharedFlow` as
  `SkieSwiftSharedFlow`, both implementing Swift's native `AsyncSequence` — meaning
  `for await value in viewModel.stateFlow { ... }` works directly from Swift with no hand-written
  callback/cancellation bookkeeping. SKIE also bridges Kotlin sealed classes to true Swift `enum`s
  with exhaustive `switch` support — directly relevant to this repo's `State`/`Event`/`SideEffect`
  sealed-class MVI hierarchies (`MobileViewModel<State, Event, SideEffect>`).
  [SKIE flows docs](https://skie.touchlab.co/features/flows)
  This materially reduces the exact gap `AGENTS.md` names as missing — "iOS needs an equivalent
  [to `AndroidViewModel`] wrapper (ObservableObject bridging `StateFlow` → `@Published`); none
  exists yet" — since SKIE removes most of the manual Flow-bridging code that wrapper would
  otherwise have to hand-write; a thin `@Observable`/`ObservableObject` class is still needed on
  top, but it becomes much smaller. No evidence of SKIE incompatibility with either CocoaPods or
  direct integration was found (SKIE operates as a Kotlin compiler plugin, which is
  packaging-agnostic by construction) — this is a reasoned inference from how SKIE integrates, not
  a directly-cited compatibility statement. **Effort to adopt: S** (add the
  `co.touchlab.skie` Gradle plugin; the real cost/benefit shows up in how much Swift bridging code
  it saves downstream).

- **Swift 6 strict concurrency** (this is the weakest-sourced part of the research — flagged
  explicitly): Kotlin/Native-exported Objective-C classes are not `Sendable` by default, and plain
  Kotlin data classes aren't automatically bridged as `Sendable` either. Swift 6's strict
  concurrency checking will flag this at concurrency-domain boundaries when Swift code calls into
  the `shared` framework from a different actor/task context. Practitioner-sourced (not
  Apple-primary-sourced) mitigations found: apply `@preconcurrency import shared` at the interop
  boundary, and confine all direct Kotlin interop to `@MainActor`-isolated bridge classes (one per
  viewmodel, which aligns naturally with the VM-wrapper work anyway). Kotlin 2.4's "Swift export"
  effort is moving toward better interop here but no confirmed compiler flag for marking Kotlin
  types `Sendable` was found. **Practical recommendation: build the new iOS app in Swift 6 mode
  from the start** (there's no legacy Swift 5 code to preserve), and scope all Kotlin interop
  through `@MainActor` bridge types as a matter of course. Effort: **S–M**, folded into the
  VM-bridge work rather than a separate line item.

- **`@Observable` vs `ObservableObject`**: the Observation framework's `@Observable` macro
  (iOS 17+) is the current idiomatic SwiftUI state-bridging pattern — property-level change
  tracking gives finer-grained view re-render diffing than `ObservableObject`/`@Published`'s
  whole-object invalidation. The natural bridge shape is an `@Observable @MainActor` class with
  plain `var` properties mirroring the Kotlin `State`, updated inside a `Task` that loops
  `for await` over the SKIE-wrapped `StateFlow`. This composes naturally with SKIE, since both
  sides just deal in `AsyncSequence` — this specific pairing is an architectural inference from how
  the two pieces fit together, not something SKIE's own docs demonstrate directly (SKIE's examples
  use `ObservableObject`/`@Published`, being older than widespread `@Observable` adoption).
  **Recommendation: use `@Observable`** — there's no legacy iOS code forcing `ObservableObject`
  compatibility. Effort: **S** (a write-time decision, not a migration).

- **Minimum iOS deployment target**: currently pinned to 14.1 in both
  `shared/build.gradle.kts` (`ios.deploymentTarget = "14.1"`) and `iosApp/Podfile`
  (`platform :ios, '14.1'`). **Recommendation: raise to iOS 17.0** — this unlocks `@Observable`
  directly (rather than needing an `ObservableObject` shim for pre-17 support), practitioner
  sources suggest very high current device coverage at that floor, and there is no existing
  installed user base on iOS to break (the app has never shipped). Separately, and worth tracking
  independently: Apple requires new App Store submissions to be built with the iOS 26 SDK / Xcode
  26 as of roughly April 28, 2026 (secondary-sourced three times in this research pass, not
  fetched directly from an Apple primary source — verify against developer.apple.com before
  treating as a hard date) — this is a build-toolchain requirement, not a deployment-target floor,
  and the repo's local Xcode 26.6 already clears it. Effort: **S**.

### Caveats carried forward from the iOS research thread (not primary-sourced this session)
Exact SKIE patch version; the Apple SDK-mandatory submission date; the SKIE+`@Observable` pairing
(architectural inference, not documented by SKIE directly); Swift 6 `Sendable` mitigation
specifics. Suggested highest-value follow-up fetches before executing this stage:
developer.apple.com's SDK policy page, SKIE's own README/CHANGELOG, and JetBrains' Swift-export/
Sendable interop docs.

---

## Upgrade sequence

Ordered by hard dependency constraints discovered above, not by convenience. Each stage should
land as its own PR and **end with CI green** (`:androidApp:assembleDebug`,
`:androidApp:testDebugUnitTest`, `:shared:linkDebugFrameworkIosSimulatorArm64`, and the iOS
`xcodebuild` job) before the next stage starts.

### Stage 0 — CI action bumps (§7)
Independent of everything else; do first as a quick, low-risk win and to reduce noise while later
stages are in flight. `actions/setup-java@v5`, `actions/checkout@v7`, `actions/cache@v6`,
`actions/upload-artifact@v7`, `gradle/actions/setup-gradle@v6`.

### Stage 0.5 — compileSdk/targetSdk bump for Play compliance (§5)
**Time-sensitive independent of the rest of this plan** — the Play API-36 targeting deadline
doesn't wait for the Kotlin/Gradle/AGP work to finish. This *can* be decomposed from Stage 1: AGP
7.4.0 already supports API 33; bumping compileSdk/targetSdk alone up to whatever ceiling the
*current* AGP 7.4.0 supports buys time, but research found AGP 8.6.0+ is required for API 35 and
above — so a meaningful jump to 36/37 is **not actually separable from at least a partial AGP
bump**. Practical recommendation: fold the full compileSdk/targetSdk 36–37 move into Stage 1
below rather than trying to decompose it, given the AGP floor table in §4, but treat Stage 1 as
urgent *because of* this deadline, not just as general modernization.

### Stage 1 — Kotlin 2.x + KSP2 + Compose-compiler-in-Kotlin + AGP 8.x/9.x + compileSdk, as one coordinated move
This is confirmed **not decomposable** by the research: Gradle 9 requires KGP ≥2.0.0 and AGP
≥8.4.0 simultaneously; AGP versions above 8.6.0 are needed for compileSdk 35+; Ktorfit's current
stable (needed in Stage 2) requires Kotlin ≥2.2.0. Concretely, in one PR:
- Bump Kotlin to 2.4.x, remove the manual `iosMain`/`iosTest` `dependsOn` wiring in
  `shared/build.gradle.kts` in favor of the Default Hierarchy Template (§1).
- Move the Compose compiler to `org.jetbrains.kotlin.plugin.compose`, drop the standalone
  `kotlinCompilerExt` pin (§1).
- Bump KSP to a 2.x-line release matching the Kotlin version (§2); smoke-test the Ktorfit KSP
  codegen boundary across `commonMain`/`android`/`iosX64`/`iosSimulatorArm64` explicitly, given the
  unresolved-status flag on Foso/Ktorfit#634.
- Bump AGP to 8.x (or straight to 9.x if the Gradle-9 move happens in the same PR — see below) and
  compileSdk/targetSdk to 36 (or 37) per §4–5.
- Migrate `buildSrc/Deps.kt` to `gradle/libs.versions.toml` in the same pass (§6), so the catalog
  is populated once with already-current numbers.
- Decide in-PR whether to fold Gradle 9 (§3) into this same stage or hold it one PR later — the
  version floors mean Gradle 9 *can* ride along with this stage once AGP ≥8.4.0 and Kotlin ≥2.0.0
  land, but doing it as an immediate follow-up PR keeps the riskiest single stage smaller.

### Stage 2 — Library majors: SQLDelight 2, Ktor 3, Ktorfit stable, Koin 4, serialization/coroutines/datetime/settings
Gated entirely behind Stage 1 (Kotlin 2.2+, KSP2 all required by Ktorfit 2.7.5 specifically).
Within this stage:
- SQLDelight 1.5.4 → 2.3.2: coordinate/import renames, Gradle DSL change, and the
  `asFlow()`/`mapToList(Dispatchers.IO)` rewrite in `TimelineStorage.kt` (§8). **No data-migration
  risk** — existing `profile_${id}.db` files are unaffected.
- Ktor 2.1.3 → 3.5.1 (§9) and Ktorfit beta16 → 2.7.5 (§10) together, since Ktorfit's version floor
  names Ktor 3.5.0 explicitly.
- Koin 3.2/3.3 → 4.2.x (§11).
- kotlinx.serialization → 1.11.0, kotlinx.coroutines → 1.11.0, multiplatform-settings → 1.3.0 (all
  low-risk, §12) alongside kotlinx-datetime → 0.8.0, which needs the `Instant`/`Clock` migration in
  `DateFormatting.kt` done explicitly, not just a version bump (§12) — **this is also the moment
  to finally implement the iOS `shortDateFormat()` actual**, replacing the `"TODO"` stub, since the
  file is being touched anyway.
- **Data-compat risk called out explicitly**: the serialization-library upgrade is believed safe
  for existing stored JSON (no breaking changelog entries found across seven minor versions,
  §12), but this should be verified with an actual round-trip test — deserialize a
  pre-upgrade-format `MastodonJson`/`ServerCredentials`/cached-`Status` blob against the
  post-upgrade library — before this stage is considered done, not just assumed from the absence
  of changelog entries.
- **Martok regen risk**: the entity-generation pipeline (`build_schema.sh` → martok →
  `shared/.../api/entities/`) emits `@Serializable` Kotlin data classes; nothing in this stage's
  research surfaced a martok-specific incompatibility with the newer serialization/Kotlin
  versions, but martok's own compatibility with Kotlin 2.x/serialization 1.11 was **not
  independently researched in this pass** — verify `./build_schema.sh` still produces
  compiling, correctly-annotated output before merging this stage, since a regen failure here
  would be silent until the next entity change is needed.

### Stage 3 — iOS packaging: direct integration (or SPM) + SKIE, and the first real iOS work
Independent of Stages 1–2 in principle (direct integration doesn't require any of the JVM/Android
library bumps), but sequencing it after Stage 1 means the Kotlin 2.x compiler is already in place
for whatever SKIE version is adopted, and after Stage 2 means the framework being packaged already
exposes the final Ktor/Koin/serialization surface rather than an intermediate one.
- Remove the `cocoapods {}` block and `iosApp/Podfile`; wire `embedAndSignAppleFrameworkForXcode`
  into an Xcode Run Script phase; translate `extraSpecAttributes["libraries"]` to
  `binaries.framework { linkerOpts += ... }` (§14). Delete the CI `syncFramework` pre-sync
  workaround entirely — it becomes structurally unnecessary.
- Add the SKIE Gradle plugin (§14).
- Raise the iOS deployment target from 14.1 to 17.0 in the (now Gradle-only, no Podfile)
  configuration (§14).
- Build in Swift 6 language mode from the start; scope Kotlin interop through `@MainActor`
  bridge types (§14).
- Build the actual iOS-milestone work this stage enables: the VM-bridge wrapper (`@Observable`
  class consuming SKIE-wrapped `StateFlow`/`SharedFlow`), then the login screen and a read-only
  timeline — see the issue backlog for the concrete breakdown.

### Stage 4 — Material 3 (deferred, decoupled)
No dependency-graph relationship to Stages 1–3 — can be scheduled independently, any time after
the Compose BOM is adopted (itself foldable into Stage 1's AGP/compileSdk work at BOM-only/M2
scope, §13). Full M3 touches `MyApplicationTheme.kt`'s custom `Colors` extensions, all
`MaterialTheme.colors.*` call sites, and `HomeScreen.kt`'s `Scaffold`/drawer structure — real
component-level rewrites, not renames. Treat as its own cosmetic-pass issue, explicitly not a
blocker for anything else in this document.

---

## Sources consulted

This document synthesizes four parallel research passes, each citing primary sources inline above
(kotlinlang.org, developer.android.com, GitHub release/changelog pages for SQLDelight, Ktor,
Ktorfit, Koin, kotlinx.serialization, kotlinx.coroutines, multiplatform-settings, kotlinx-datetime,
and Touchlab's SKIE docs). Every numbered finding above states its confidence level; items marked
"unverified," "secondary-sourced," or "could not confirm" should be re-checked against a primary
source at the time the corresponding stage is actually executed, since this document was compiled
in a single research pass in August 2026 and library ecosystems move continuously.
