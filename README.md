# ConstrainedSortedList

This is a simple (albiet verbosely named) Java class that maintains a short
leaderboard of large set of objects. For example, if I was making a kitchen
timer app, I could insert the user's entire history and then be able to
continuously display the top three timers they use, updating as the history
grows.

The name describes the short sorted list that is maintained, while all the other
elements are in a hashmap.

From the comment block:

> ConstrainedSortedList is designed to store sorted key/value pairs with inserts in O(maxLength)
> and has linear time lookups.
>
> This is accomplished by keeping a hashmap of the key-values, where the values are objects which
> store both the key and the value. We also keep an arraylist of the values (which have both key
> and value). When a value is updated, we retrieve the object by its key from the hashmap and
> change it. This also changes its value in the arraylist (if it is in the list). We then insert
> the value into the arraylist priority-queue style, trimming the array list to stay at maxLength.
>
> Because the dictionary lookups are linear and inserting the value takes at most maxLength
> operations, the insert is quick. Lookups are then quick also, they're simply array indexes.
>
> This does require a class that implements ObjectCreatorIncrementerAndComparator, which is hairy
> but also allows it to be extensible.
