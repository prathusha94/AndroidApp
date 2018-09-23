# Android_friendFinder
Android app that helps find friends that are within a certain radius from the user and displays their location on a Map
*) The mobile app will upload the latitude and longitude information to a backend sever/database. 
*) A mysql database that contains details of every registered user.
*)A webservice written in php that sends login/registered information to the backend and also fetches details of friends close by to be displayed in the app

## Requirements : Lampp , ngrok

#### To start server : sudo /opt/lampp/lampp start
( place the php files in the folder : opt/lampp/htdocs )

#### To start ngrok : 
- -> Create auth token first
- -> ` ./ngrok authtoken “< auth token >” ` 
- -> ` ./ngrok http 80 `
- -> Copy either http/https link based on the connection used in Android studio
and paste in “LoginActivity” and “registerActivity” . 
#### Android phone : Enable Location permissions

### Demo
Red pin is the current user's location and all the blue pins are locations of friends nearby.

![image](https://user-images.githubusercontent.com/37677121/45931847-bbd6dc00-bf41-11e8-95c7-49b95802ea5c.png)
