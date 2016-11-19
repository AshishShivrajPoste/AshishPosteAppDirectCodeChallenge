# AppDirectCodingChallenge
Coding Challenge of AppDirect


#Instalation

The Project is created in Eclipse using Maven Build tool.

Run 

mvn clean package

Deploy the war generated into tomcat server.

I am using ngrok for creating the tunnel.

# Configuration

Please change the consumerKey and consumerSecret in config.properties file under /src/main/resource.



# AppDirect Event Notification URL configured

As I am using ngrok for tunnelling please update the URL accordinglly.

Subscription Create Notification URL

http://690c679c.ngrok.io/codechallenge/webapi/subscription/create?url={eventUrl}

Subscription Change Notification URL

http://690c679c.ngrok.io/codechallenge/webapi/subscription/change?url={eventUrl}

Subscription Cancel Notification URL

http://690c679c.ngrok.io/codechallenge/webapi/subscription/cancel?url={eventUrl}

Subscription Status Notification URL
http://690c679c.ngrok.io/codechallenge/webapi/subscription/status?url={eventUrl}

User Assignment Notification URL

http://690c679c.ngrok.io/codechallenge/webapi/user/assign?url={eventUrl}

User Unassignment Notification URL

http://690c679c.ngrok.io/codechallenge/webapi/user/unassign?url={eventUrl}

