# CustomGalleryApp

This is a custom Android gallery app that displays images and videos in a single screen as album
views. The app categorizes media into folders, allowing the user to view images and videos based on
their folder structure in the device. The gallery provides a user-friendly interface to view all
images, videos, camera photos, and specific albums.

Features

Album View: The app will show all the media folders present in the device.
For example, if the device contains images in a folder named Screenshots, it will show all those
images under the Screenshots album.
Displays the album name and the number of images or videos in each album.
All Images: A folder that displays all the images available in the device, excluding cache,
thumbnails, and no-media files.
All Videos: A folder that displays all the videos available in the device, excluding cache,
thumbnails, and no-media files.
Camera Folder: Displays all the images taken using the camera, grouped under the Camera album.
Detailed View: Clicking on any album will redirect to the detailed view where the media content of
that album (images and videos) is displayed.
UI Design

The app’s UI is designed to be clean and easy to navigate:

Main Screen: Displays a list of all albums with the folder name and the number of media files (
images or videos) in each folder. This is done in a grid or list format for ease of use.
Album Detail Screen: When an album is selected, the detailed view screen is shown, displaying all
media files (images/videos) present in that album, along with options to play
videos.
Folder Structure

All Images: Displays all images in the device excluding cache, thumbnails, and no-media images.
All Videos: Displays all videos in the device excluding cache, thumbnails, and no-media images.
Camera: Displays all photos taken by the local camera app.
Album Folders: Displays all other folders that contain images or videos.
Prerequisites

Android Studio (version X.X.X or later)
Android SDK
Basic understanding of Android development and Java/Kotlin
Device with images/videos or an emulator with media files for testing
Setup Instructions

1.Clone the repository:
   git clone https://github.com/bahaagabal/CustomGalleryApp.git
   cd gallery-app
2.Open the project in Android Studio:
   Open Android Studio.
   Select File > Open and choose the project directory.

3.Run the app:
After ensuring that your Android device or emulator has media files (images/videos), simply press the Run button in Android Studio to build and launch the app.
4.Test the functionality:
Navigate through the "All Images", "All Videos", and various album folders.
Check that the app properly categorizes and displays media files in each album.
App Design

Main Activity
Displays a grid/list of albums (folders).
Each album shows the name and the number of images/videos inside.
Album Detail Activity
Displays the contents (images/videos) of a selected album.
Videos are shown when you click on a video item , allowing users to watch them.

Code Overview

- we used clean architecture 
- Mvi Pattern 
- Dagger Hilt for dependency injection
- Exo Player For Displaying Videos 
- Jetback compose 
