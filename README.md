# Tangent

A Kotlin Multiplatform Mastodon client — and, just as deliberately, a **testbed**.

<img alt="status" src="https://img.shields.io/badge/android-working-brightgreen"> <img alt="status" src="https://img.shields.io/badge/iOS-stub-red"> <img alt="status" src="https://img.shields.io/badge/toolchain-2023--era-orange">

## What it is

Tangent started (Jan 2023) as a toy Mastodon client and a proving ground for
[martok](https://github.com/asarazan/martok), which generates the entire Mastodon entity layer
from TypeScript schemas (`api/entities/*.d.ts` → Kotlin `@Serializable` classes).

The **Android app works**: instance picker + OAuth login, home timeline with an offline SQLDelight
cache, pagination with gap placeholders, optimistic fave/reblog, and unreasonably careful HTML +
custom-emoji rendering in Compose.

The **iOS app is the untouched KMP template stub** — which is now the point:

## The testbed mission

Tangent's second life is as a small-but-real codebase for exercising automated SDLC:

- **Agent-driven development** — GitHub issues as the source of truth, worked by agentic tooling
  (Warp Software Factories, Claude Code, etc.).
- **iOS parity from zero** — SwiftUI-native UI bound to the existing shared KMP viewmodels
  (not Compose Multiplatform, by design). [Parive](https://parive.ai/) provides the parity
  baseline and progress metric.
- **[articulate](https://github.com/asarazan/articulate)** — single-source strings
  (`strings.xml` → `.xcstrings`) once the iOS app has real copy.
- **[martok](https://github.com/asarazan/martok)** — continues to own the API entity layer
  (`./build_schema.sh` regenerates it).

## Architecture in one paragraph

`shared/` holds everything platform-agnostic: martok-generated entities, a Ktorfit/Ktor API
client, an SDK layer (`MastodonServer` for app registration + OAuth, `Mastodon` per account
session), a SQLDelight timeline cache, and MVI viewmodels
(`MobileViewModel<State, Event, SideEffect>` with a single `reduce`). `androidApp/` is pure
Compose binding to those viewmodels through thin `AndroidViewModel` wrappers; `iosApp/` will do
the same from SwiftUI. DI is Koin; settings are `multiplatform-settings`.

For the full agent-facing tour — module map, feature matrix, known issues, toolchain caveats —
see **[AGENTS.md](AGENTS.md)**.

## Building

> ⚠️ The dependency stack is pinned to early-2023 versions (Kotlin 1.7.20, AGP 7.4.0) and the
> build has not been verified on modern toolchains. Modernization is the first item on the
> project backlog.

```bash
./gradlew :androidApp:assembleDebug        # Android
./gradlew :androidApp:testDebugUnitTest    # tests (HTML/emoji parsing)
./build_schema.sh                          # regen entities (needs martok CLI)
cd iosApp && pod install                   # iOS: then open iosApp.xcworkspace
```

## License

Unlicensed toy project; ask before doing anything weird with it.
