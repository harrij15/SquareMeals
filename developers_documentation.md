# SquareMeals Development Documentation

## Classes

HomepageActivity: displays recipe recommendations based off a user’s diet preference and also shows a user’s saved recipes. This class also has a search feature.
	-display better images (the images are too small)
	-no known bugs (nkb)

HomepageGuestActivity: displays only recipe recommendations to a guest and provides a search feature
	-dumbed down homepage activity)
	-also needs better images
	- guests should be able to filter as well

HomepageButtonAdapter: Sets up the image buttons that are displayed in HomepageActivity and HomepageGuestActivity
	-Not used as far as we know

HomepageListArrayAdapter: Sets up a ListView in the MyCookBook tab of the homepage
	-hasn’t been used (supposed to be for personal cookbook.)

UserDatabaseHelper: A database helper class using SQLiteOpenHelper that functions as a facilitator for inserting/updating/deleting/querying information from the SQLite database regarding users.
	-can’t upgrade without wiping (wants to fix this)

User: A class to store a user’s information such as username, real name,  password, email, and diet preference. The class’s primary function is to facilitate the inserting process of a new user into the SQLite Database. (actual object being stored)

RecipeDatabaseHelper: (incomplete/unused class) a helper class using SQLiteOpenHelper that functions as a facilitator for inserting/querying information from the SQLite database regarding recipes. Might be used in the future when users have the option of uploading their own recipes.
	-not used yet
	-can’t update either
	-want to switch to being dependent on the database in the future

Recipe: (incomplete/unused class) a class to store recipe information

Slides:
Survey results
Search (filter)

* -> what shreya wants to improve (UI/appearance)
*LoadingActivity
	-greets user
	-loads data for homepage for top picks
*LoadResultsActivity
	Loading search results. LoginActivity
	Checks database, if pw matches.
	-Needs a UI
*RecipeInfoActivity
	Displays name, pic, ingredients, time it takes
	Ugly hyperlink
	-improve UI
	-include procedure
	-get bigger image
SearchActivity
	Uses SearchResult (class) to display recipes.
	-only displays a few recipes (want it display more than just 6 (6 bc of yummly))
	-improving UI

*SearchResult
	Display: Picture on left side, name and description.
	Clicking on a result => display RecipeInfoActivity
Improve UI
SearchResultsAdapter
	Handling searchresult, sets name, display and icon
SelectPreferencesActivity (already did this)
	Bug: Preference (Lacto) still comes up with cheese in the search
SignUpActivity
	Putting info in for database. (multiple checks pw/username stuff)
	-check if username is taken already before hitting submit button
UpdateProfileActivity
	Updates changes to profile. Pushes to database.
WelcomeActivity
	Logo, welcoming page. Disappears.

MyDietActivity -> Handles SelectPreferenceActivity
	-displays diet preferences (extended to multiple)
	-clicking on pref allows you to change it
	-> shoots to SelectPreferenceActivity
SelectPreferenceActivity
	Connected to both MyDietActivity and SignUpActivity (flag specifies where you’re coming from)
	-missing: custom diet (don’t have health diets ie. weight loss etc)

ProfilePage
	Displays user information (taken from database)
	Can change profile user’s picture
	-wants profile picture to appear on toolbar
	-more info or profile page w/ search results (recent searches) and preferences (recommendations)
Chatbox
	-not fully implemented yet
	-want it to be like a blogpost (social media?) to share recipes
	+chat feature