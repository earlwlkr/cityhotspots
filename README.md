# City Hot Spots #

Suggest places of interest in city.

## Getting started

1. Download Google Play services from SDK Manager.
2. Go to https://console.developers.google.com, create a project if you haven't.
3. Go to **APIs and auth**, enable the following APIs:
  * Google Maps Android API v2
  * Directions API
  * Geocoding API
  * Google Places API for Android
  * Distance Matrix API
4. Go to **Credentials** and retrieve the API key.
5. Edit the *geo_api_key* value in *apikeys.xml* to yours.
6. Build and run project.

## Contributing

Before you push to the remote repository, make sure you don't commit changes to *apikeys.xml* by doing the following:

```
git update-index --assume-unchanged app\src\main\res\values\apikeys.xml
```

Follow this list of Android development's best practices:

https://github.com/futurice/android-best-practices

## License

MIT License