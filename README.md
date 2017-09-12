# Challenge
Hello :wave:

Usually, I tend to use MVP architecture, but I was curious about the MVVM architecture. I saw that Google announced on this year’s I/O a set of [Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html), which seemed like a great opportunity for me to see what I could achieve using this architecture and this library.

### Implementation Details

MVVM architecture:

- **Model:** Responsible for providing any data used in the application. 
- **ViewModel:** Holds the view state and talks with data layer.
- **View:** Represented by activities and fragments, meaning, anyone who is able to update the UI.

**Dependency Graph:** All application dependencies are built using [dagger.android](https://google.github.io/dagger/android.html) that provides less setup boilerplate and injects dependencies easier.

**ViewModel:** Using the new component [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel.html) combined with [LiveData](https://developer.android.com/topic/libraries/architecture/livedata.html), when the user triggers an asynchronous request and then rotates the screen, i.e., configChanges, we can ensure that this request will not be lost and will answer accordingly.

**View-ViewModel Communication:** I chose [LiveData](https://developer.android.com/topic/libraries/architecture/livedata.html) as the main way to communicate states between View and ViewModel, because it’s lifecycle aware and provides an easy way to observe changes. 

**ViewModel-Model Communication:** I chose [RxJava](https://github.com/ReactiveX/RxJava) to handle data between these layers, because streams provide a great power through operators to transform data.

**Repository:** Consistent central source of data that decides which data source to query.

**Instrumented Tests:** Used Espresso to create UI tests integrated with [Android Test Orchestrator](https://developer.android.com/training/testing/junit-runner.html#using-android-test-orchestrator). Repositories are mocked using Mockito in a shared test-common package.

**Unit Tests:** Used JUnit and Hamcrest for assertions. Repositories are mocked using [Mockito](http://site.mockito.org/) in a shared test-common package.


### Problems I faced:  

#### Restoring state after Memory pressure

**Problem:** By default ViewModel survives configChanges, but it cannot recover from a memory pressure. The default implementation doesn’t provide a clear way to decide when to get data from savedInstanceState or keep the ViewModel state. 

**Solution:** On the ViewModel’s constructor check if the Activity bundle is not null, then restore from the bundle, this represent a memory pressure.

**Explanation:** The bundle of the Activity will have something only if the ViewModel is recreated, meaning it suffered memory pressure. In code words: (savedInstance != null && viewmodel constructor called) == memory pressure 


### Conclusions about MVVM

Easy learning curve, not appropriated to complex views, easy to write tests. Maybe a better option is use a mix with MVP, using ViewModel as a data class that holds just the view state, and the presenter can manipulate this state. But these are only initial thoughts, I’ll try to think of an architecture that takes advantage from MVP and MVVM ideas.

### What could be improved:
- Greater percentage of test coverage.
- Better handling of LiveData nullable Observer.
- Loading view states for api and location requests.



