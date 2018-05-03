# KnowYourGovernment1.0
Mobile Application Development : CS 442

Platform - Android Studio(https://developer.android.com/studio/index.html)

- This app will display a list of political officials at each level of government at current location. User's location will be determined by android location services.
- The Google Civic Information API is used to acquire the government official data via REST service and JSON results.
- The "Location" options menu item opens a dialog box that allows user to enter a city/state or zip code for manual search.
- Recycler View list entries will display a list of government officials. Clicking on a political's entry from the list will open a detailed view of that individual government representative.
- The detailed view of official contains basic official data(Office, Name & Party), photo(if available else default image), contact information and social media(Facebook, Twitter, Google+ & YouTube).

            -> Photo, Address, Email address, Phone number, Website and Social Media icons are all clickable.
            -> Photos are loaded from Picasso, clicking it will open an enlarged version of it.
            -> Address will open up the address on google maps.
            -> Email app with new message is opened with pre-loaded email address.
            -> Phone number will be loaded on the PhoneApp.
            -> Website when clicked will open in the browser.
            -> Clicking on the social media icon's will open related app(if installed) or by default will open the site in a browser.
