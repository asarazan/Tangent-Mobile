#!/bin/sh

rm -r shared/src/commonMain/kotlin/social/tangent/mobile/api
npx martok api -t=true -p social.tangent.mobile.api -o shared/src/commonMain/kotlin/social/tangent/mobile/api