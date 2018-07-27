# Sample-Application
A sample application in Kotlin to explore the features of the language.

This is my personal project to explore Kotlin through writing a simple application from the ground-up. 
I also try to properly structure and comment my code. Here, I would like to build a Reactive (single-activity with fragments) application loosely following MVVM.
This repo more or less demonstrates how I would write a similar application if I was in charge of everything as opposed a real work environment.

I hope to revisit this repo to refactor the codebase as my skills improve.

Here is a list of some of the libraries used in the project: 
* [Room](https://developer.android.com/topic/libraries/architecture/room.html) - for data storage
* [Live Data](https://developer.android.com/topic/libraries/architecture/livedata.html) - to expose data to views
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel.html) - to separate operations from views
* [RxJava](https://github.com/ReactiveX/RxJava) - to support event-based architecture
* [Retrofit](http://square.github.io/retrofit/), [OkHttp](http://square.github.io/okhttp/) - to power network requests
