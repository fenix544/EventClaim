# EventClaim

## Introduction

Mod for automatic code input on finerpg.pl

## Setup

1. Download or clone this repository
2. Open it as a Project
3. The gradle setup should be running automatically, if not, use `./gradlew`
4. Run the gradle task `gradle setupDecompWorkspace` (if you are getting the error "Heap Memory too low", increase the memory by adding `-Xmx4G` or `-Xmx8G` to the gradle task, depending on how much RAM you have; Right-Click the Gradle Task -> Modify Run Configuration... -> Modify Options -> Add VM Options)
5. If the setup is done, try to run the client with the example mod (use my prebuilt task `gradle buildCopyToRunModsAndRun` if you want -> combines building, copying to the run mods folder and running)

## License

You can read all the applying licenses in the file `LICENSE.md`
