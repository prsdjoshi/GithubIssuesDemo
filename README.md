# Github issues Demo
This App displays GitHub issues of okhttp (https://github.com/square/okhttp/issues) repository from square.
     Features:
      1. Show the issues provided by API in a List.
      - Show complete title.
      - Show first 200 Characters of the issue description.
      - Show user info. (userName and avatar)
      - updated_at date in mm-dd-yyyy format.
      2. App should show all the comments and issue info when the user clicks on the issue from the issue list.
         App will work in offline mode also.

        APIs info.     https://api.github.com/repos/square/okhttp/issues

     This will include list of all issues.
     In response to the above URL. Each json object’s comments_url will include a list of comments.

     3. Retrofit for API and Glide for Image loading.
     4. Language Used: Kotlin .
     5. Used MVVM Architecture



