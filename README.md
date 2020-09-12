# MyStore
A custom app store that fetches app data from play.google.com

## TODO List:
- [x] Get app info from play store
- [x] Search app from play store
- [ ] Get apk from google play store servers
- [ ] Notify when new version of app is available

### Ideas
In order to get the apk from play store I'm figuring out how to use [android-market-api](https://code.google.com/archive/p/android-market-api/), which is depecrated. 
I'm going to see projects like [google-play-crawler](https://github.com/Akdeniz/google-play-crawler) and [googleplay-api](https://github.com/NoMore201/googleplay-api)


## Current job
Each page having url like: play.google.com/store/search?q={app_name}&c=apps has in its html some scripts. The code below takes the one that we need to get apps searched:
### 1. Get script content

```java
public static String parse(String html) {
    Pattern pattern = Pattern.compile("AF_initDataCallback[\\s\\S]*?<\\/script");
    Matcher matcher = pattern.matcher(html);

    String output = null;
    int counter = 0;
    while(matcher.find()){
        if(counter++ == 5){
            output = matcher.group(0);
        }
    }
    return output;
}
```

### 2. Get json from script content
```java
String json = parse(html);
json = json.replaceAll("AF_initDataCallback", "").replaceAll(";</script", "").substring(1);
```

### 3. Fetch json and get app list
```java
public static List<Item> fetchJson(String json) throws JSONException {
    JSONObject obj = new JSONObject(json);
    
    String query = BASE_URL + "apps/details?id=";
    List<Item> items = new ArrayList<>();

    JSONArray dataArray = obj.getJSONArray("data");
    JSONArray itemsArray = dataArray.getJSONArray(0).getJSONArray(1).getJSONArray(0).getJSONArray(0).getJSONArray(0);
    Log.d(TAG, "fetchJson: itemsSize: " + itemsArray.length());

    for(int i=0; i<itemsArray.length(); i++){
        JSONArray item = itemsArray.getJSONArray(i);

        String name  = item.getString(2);
        String image = item.getJSONArray(1).getJSONArray(1).getJSONArray(0).getJSONArray(3).getString(2);
        String pack  = item.getJSONArray(12).getString(0);

        items.add(new Item(name, null, query.concat(pack), image));
    }

    return items;

}
```

That's it.
