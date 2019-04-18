# Starwars app

## Steps to run the app

1. Download Android Studio
2. Clone or Download the project
3. Build the project and click play on the top in android studio or use commands 
   `./gradlew installDebug`
   
## Libraries used
- Dagger2 (Dependency Injection)
- Retrofit2 (Network calls and management)
- Glide (Image loading and caching)
- Android Architecture Components (LiveData, ViewModel)
- Stetho (Debugging)
   
## Architecture
Following the MVVM architecture

The app has following packages:
- data: It contains all the data accessing and manipulating components including the remote api.
- di: Dependency providing classes using Dagger2.
- viewmodel: Contains viewmodel.
- repository: Contains all repository which access api to provide data to viewmodel.
- view: Contains all views (ui elements).