# FragmentContainerViewIssue



Hiding a fragment with setCustomAnimations works fine using FragmentContainerView:

```kotlin
supportFragmentManager.beginTransaction()
  .setCustomAnimations(R.anim.fragment_pop_enter, R.anim.fragment_pop_exit)
  .hide(fragment)
```

1. Open Fragment B
2. Animation plays correctly and Fragment B is shown
3. Click "Hide"
4. Fragment B is hidden, animation plays correctly and Fragment A is shown

However, when we try to remove a fragment, the ordering of the View is different. Fragment B is shown below Fragment A.

```kotlin
supportFragmentManager.beginTransaction()
  .setCustomAnimations(R.anim.fragment_pop_enter, R.anim.fragment_pop_exit)
  .remove(fragment)
```

1. Open Fragment B
2. Animation plays correctly and Fragment B is shown
3. Click "Remove"
4. Fragment B is removed but it's placed below Fragment A

## Comparison between Fragment containers

| FragmentContainerView | FrameLayout |
| ------------- | ------------- |
| ![BugFragmentContainerView](https://user-images.githubusercontent.com/10662096/71026693-a54a1100-2101-11ea-80d9-93ceb6e4f8bd.gif) | ![FrameLayoutFix](https://user-images.githubusercontent.com/10662096/71026715-b135d300-2101-11ea-8e2f-098decde4c8e.gif)

|
