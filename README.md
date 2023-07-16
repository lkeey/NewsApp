# Alertsy

[![CodeFactor](https://www.codefactor.io/repository/github/lkeey/newsapp/badge)](https://www.codefactor.io/repository/github/lkeey/newsapp)

It is an android application where you can find breaking news, save them or even search something

The idea is to simplify the search for news

## Project characteristics and tech-stack

<img src="https://raw.githubusercontent.com/lkeey/NewsApp/master/app/src/main/res/drawable/logo.png" width="400" align="right" hspace="20">

* Jetpack
  * Navigation - navigation inside Activity
  * LiveData - notify observers about database changes
  * Lifecycle - event handling based on lifecycle
  * ViewModel - store and manage UI-related data in a lifecycle conscious way
  * Android KTX - set of Kotlin extensions
  * Fragment - using multiple screens inside activity
  * ViewBinding - getting links to interface elements
* Layouts
  * LinearLayout
  * ConstraintLayout
  * FrameLayout
  * RecyclerView
* Storage of information
  * SQLite
  * Room - saved articles
* Modern Architecture
  * Layers architecture
  * MVVM
* Retroit - send requests
* GSON - requests converter
* Glide - download images by link

