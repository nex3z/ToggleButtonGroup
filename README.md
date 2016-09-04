# ToggleButtonGroup

A group of toggle buttons, supports multiple / single selection. 

# MultiSelectToggleGroup

You can create a group of multiple selection toggle buttons with `MultiSelectToggleGroup`.

![multi](images/multi.gif)

Define the `MultiSelectToggleGroup` as follows:

```xml
<com.nex3z.togglebuttongroup.MultiSelectToggleGroup
    android:id="@+id/multi_selection_group"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:textSize="16sp"
    app:buttonSize="40dp"
    app:enableAnimation="true"/>
```

The use `setButtons()` to add buttons to the group:

```java
MultiSelectToggleGroup multiSelect = (MultiSelectToggleGroup) findViewById(R.id.multi_selection_group);
List<String> weekdays = Arrays.asList("S", "M", "T", "W", "T", "F", "S");
multiSelect.setButtons(weekdays);
```

# SingleSelectToggleGroup

You can create a group of single selection toggle buttons with `SingleSelectToggleGroup`.

![single](images/single.gif)

Define the `SingleSelectToggleGroup` as follows:

```xml
<com.nex3z.togglebuttongroup.SingleSelectToggleGroup
    android:id="@+id/single_selection_group"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:textSize="16sp"
    app:buttonSize="40dp"
    app:enableAnimation="true"/>
```

The use `setButtons()` to add buttons to the group:

```java
SingleSelectToggleGroup singleSelect = (SingleSelectToggleGroup) findViewById(R.id.single_selection_group);
List<String> choices = Arrays.asList("A", "B", "C", "D");
singleSelect.setButtons(choices);
```