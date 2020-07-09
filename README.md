# FloatingWindow (Android)

A Kotlin written floating window view for Android.

## Screenshot



## Usage

Layout XML definition:

```
<com.johnny.floatingwindowinapp.FloatingWindowContainer
    android:id="@+id/view_window_container"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <FrameLayout
        android:layout_width="240dp"
        android:layout_height="128dp">
        <!-- ... -->
    </FrameLayout>
</com.johnny.floatingwindowinapp.FloatingWindowContainer>
```

It will use first child of layout element for floatingWindow.  
Use `FrameLayout` or `ConstraintLayout` to place your rest layout.

### MarginCalculation

You can define your own `MarginCalculation` max and min margin calculation.  
It provide two calculation method in example code.

- MarginCalculationToEdge

	- **Min left:** 0  
	(Stick to left edge.) 
	- **Min top:** 0  
	(Stick to top edge.) 
	- **Max left:** containerWidth - floatingWindowWidth  
	(Stick to right edge.) 
	- **Max top:** containerHeight - floatingWindowHeight  
	(Stick to bottom edge.)
	
- MyMarginCalculation

	- **Min left:** -floatingWindowWidth + 50dp  
	(Overflow layout with reveal 50dp right stitch)
	- **Min top:** -floatingWindowHeight + 50dp  
	(Overflow layout with reveal 50dp bottom stitch)
	- **Max left:** containerWidth - 50dp  
	(Overflow layout with reveal 50dp left stitch)
	- **Max top:** containerHeight - 50dp  
	(Overflow layout with reveal 50dp top stitch)
	
### setFloatingWindowMargin()

You can move floating window by manual using `setFloatingWindowMargin()` method.

```
fun setFloatingWindowMargin(
        leftMargin: Int,
        topMargin: Int,
        animated: Boolean = false,
        completeCallback: (() -> Unit)? = null) {
    // ...
}
```

See the sample code for details.


