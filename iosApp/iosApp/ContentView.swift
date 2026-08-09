import SwiftUI
import shared

struct ContentView: View {
	let platform = PlatformKt.getPlatform().name

	var body: some View {
		Text("Tangent on \(platform)")
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}