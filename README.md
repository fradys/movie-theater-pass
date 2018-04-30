# movie-theater-pass
This App was developed as a final project of Mobile Programming class from school.

Movie Theater Pass is an App that would support a fictional service like "Movie Pass", the service consists in let users watch one movie per day paying a little less than $ 15 for 2D movies and $ 22 for 3D movies.

The App has an initial splash screen where the database is created and initialized at the first execution.
The following screens are to sign in or to subscribe to the service. 
After login the first screen is a list of all movies on display sorted by genres, that list is fetched dynamically using The Movie Database API (https://www.themoviedb.org).
Clicking on any movie would show details of that movie, all available theatres and show times. If the user selects all required information and clicks on Reserve Ticket button it would lead to another screen showing the summary of the ticket (movie, date, show time) and a QRCode to be used at the movie theatre.
Additionally, there is configuration menu bar with settings options, Billing Information, Profile, Reserved Tickets and Logout.

This app was created using the following technologies and techniques: 
   - The Movie Database API (https://www.themoviedb.org)
   - QR Code generation using the Zxing library (https://github.com/zxing/zxing) 
   - Database integration using Room Persistence Db (https://developer.android.com/training/data-storage/room/)
   - Recycler Views to a smoother experience when scrolling through the movie's posters.
