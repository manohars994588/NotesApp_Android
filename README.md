# NotesApp — Android Studio starter project

This project recreates the three-screen notes-app concept from the supplied reference:
- Welcome screen with blue gradient backdrop and rounded white panel
- Notes dashboard with search, colored note cards, pin indicator and quick actions
- Note editor with title/body, pin toggle, note colors, save and delete

## Open in Android Studio
1. Extract `NotesApp_Android.zip`.
2. Open the extracted `NotesApp_Android` folder in Android Studio.
3. Allow Gradle sync to finish. Use JDK 17 if Android Studio asks.
4. Select an emulator or connect an Android phone with USB debugging enabled.
5. Press Run.

## Notes
- Kotlin + XML-compatible Android Views (the screen layouts are assembled in Kotlin).
- Room Database stores notes locally on the device.
- The project needs internet during the first Gradle sync to download Android dependencies.
- This is a functional starter recreation. Exact pixel matching across all devices requires tuning against the original app on a known screen size.
- Package name: `com.example.notesapp`
