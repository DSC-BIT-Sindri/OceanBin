# OceanBin
>
>- Although it may not be obvious to us in our day-to-day lives, the health of the ocean has significant consequences on human health. 
>- It is estimated that 1.15 to 2.41 million tonnes of plastic are entering the ocean each year and this is accumulating into huge offshore zones. The largest is known as the Great Pacific Garbage Patch which has an estimated surface area of 1.6 million square kilometres.
>- According to the United Nations, at least 800 species worldwide are affected by marine debris, and as much as 80 per cent of that litter is plastic.


### So, what is our solution??
>
>- We aim at a commercial recycling program and better waste materials management.
>- Our app targets the collection of ocean debris from fishermen and trash from beaches with the help of volunteers.
>- So we came up with our solution, <b>"OceanBin"</b>.<br> 
>- OceanBin is an android application that aims to provide sustainable waste management solutions.

### Google Technologies Used:-
>
>- Frontend - Jetpack Compose | Android | Kotlin
>- Backend - Firebase | Map SDK, Google Map API | OpenWeather API
>- Database - Firebase Firestore | Real-Time Database

## Related Screenshots!

### Splash Screen
<img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/splash.jpeg" width ="250px" height ="450px">

----------------------------------------

### Selection Screen
<img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/fishernam-volunteer.jpeg" width ="250px" height ="450px">

### OnBoarding Screen

#### Fisherman
<img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/fisherman-onboarding-1.jpeg" width ="250px" height ="450px"> <img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/fisherman-onboarding-2.jpeg" width ="250px" height ="450px"> <img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/fisherman-onboarding-3.jpeg" width ="250px" height ="450px"> <img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/fisherman-onboarding-4.jpeg" width ="250px" height ="450px"> 

#### Volunteer
<img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/volunteer-onboarding-1.jpeg" width ="250px" height ="450px"> <img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/volunteer-onboarding-2.jpeg" width ="250px" height ="450px"> <img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/volunteer-onboarding-3.jpeg" width ="250px" height ="450px"> 

------------------------------------------

### Login/Signup Screen
<img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/signin-signup-welcome-screen.jpeg" width ="250px" height ="450px"> <img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/signup.jpeg" width ="250px" height ="450px"> <img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/signin.jpeg" width ="250px" height ="450px"> <img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/forgot-password.jpeg" width ="250px" height ="450px">

------------------------------------------

### Home Screen
<img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/home-location-permission.jpeg" width ="250px" height ="450px"> <img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/home.jpeg" width ="250px" height ="450px">   <img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/home-screen-weather-update.jpeg" width ="250px" height ="450px">

------------------------------------------

### News Screen
<img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/news-1.jpeg" width ="250px" height ="450px"> <img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/news-2.jpeg" width ="250px" height ="450px">

------------------------------------------

### Pickup Vechile Screen
<img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/tracking-screen.jpeg" width ="250px" height ="450px"> <img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/chhose-time-for%20pickup.jpeg" width ="250px" height ="450px"> <img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/pick-time-too.jpeg" width ="250px" height ="450px"> <img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/pickup-scheduled.jpeg" width ="250px" height ="450px">

------------------------------------------

### Weather Screen
<img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/weather-screen.jpeg" width ="250px" height ="450px">

------------------------------------------

### Profile Screen
<img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/profile-qr.jpeg" width ="250px" height ="450px"> <img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/profile.jpeg" width ="250px" height ="450px"> <img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/edit-profile-details.jpeg" width ="250px" height ="450px"> <img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/ave-changes-profile.jpeg" width ="250px" height ="450px"> <img src="https://github.com/anjali1361/OceanBin/blob/main/OCEANBIN%20IMAGES/user-details-updated.jpeg" width ="250px" height ="450px">

------------------------------------------

### APP FLOW:-
>
>- The app starts with detailed and interactive onboarding screens guiding the user on how to operate the app. We’ve implemented a comprehensible app interface for a better user experience using <b>Jetpack Compose Kotlin</b>
>- We have implemented a role-based Sign-in/Sign-up feature for the user, using <b>Firebase Email Authentication</b> having email verification and forgot password functionalities. After successful registration, the user will be redirected to Home Screen.
>- Home Screen provide the user with the direct option to schedule the OceanBin pickup vehicle. After clicking the related button, the user will be redirected to the vehicle schedule screen where the user can choose his preferred location for pickup and time/date slot accordingly. <b>Map SDK of Google Cloud Map API</b> is used for sending the user’s preferred location for the trash to be picked by the pickup vehicle.
>- On the home screen, we are displaying Local News with the use of <b>Firebase Firestore</b> and  & Weather Forecasts using <b>OpenWeather API</b>. We want the news for a specific group of users, i.e., fishers and also about topics of their interest like the weather forecast of the sea/ocean where they go fishing so that they can ensure their safety.
>- On the profile screen, all user-specific details will be displayed. The user can edit their details whenever wants to do.
Along with the user’s account details, total trash sold and the profit margin/wallet will also be updated using <b>Firebase Firestore & Realtime database.</b>

### FUTURE SCOPE:-
>
>- In future, we also intend to implement multilingual support.
>- Wallet transaction/update feature using Google Pay API, in the Admin Flow.

#### We all know recycled plastics reduce energy during manufacturing by 66%. That means less fuel burnt and less harm to the local environment. The collected marine plastic waste will be supplied to the nearest recycling industry.

#### Our solution aims to solve the "Life Below Water" goal listed under "The 17 Sustainable Development Goals of the United Nations".


### Link to video


### APK file
https://drive.google.com/file/d/1GT0SuL3HmxlHlQe_ogATBKiw0yO4zCKn/view?usp=sharing
