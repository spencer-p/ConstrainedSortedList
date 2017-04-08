import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Spencer Peterson on 4/7/17.
 *
 * ConstrainedSortedList is designed to store sorted key/value pairs with inserts in O(maxLength)
 * and linear time lookups.
 *
 * This is accomplished by keeping a hashmap of the key-values, where the values are objects which
 * store both the key and the value. We also keep an arraylist of the values (which have both key
 * and value). When a value is updated, we retrieve the object by its key from the hashmap and
 * change it. This also changes its value in the arraylist (if it is in the list). We then insert
 * the value into the arraylist priority-queue style, trimming the array list to stay at maxLength.
 *
 * Because the dictionary lookups are linear and inserting the value takes at most maxLength
 * operations, the insert is quick. Lookups are then quick also, they're simply array indexes.
 *
 * This does require a class that implements ObjectCreatorIncrementerAndComparator, which is hairy
 * but also allows it to be extensible.
 */

class ConstrainedSortedList<K,V> {
    private HashMap<K,V> map = null;
    private ArrayList<V> array = null;
    private ObjectCreatorIncrementerAndComparator<K,V> objectCreatorIncrementerAndComparator = null;
    private int maxLength = 0;

    public ConstrainedSortedList(int maxLength,
                 ObjectCreatorIncrementerAndComparator<K,V> objectCreatorIncrementerAndComparator) {
        this.maxLength = maxLength;
        map = new HashMap<K,V>();
        array = new ArrayList<V>(maxLength);
        this.objectCreatorIncrementerAndComparator = objectCreatorIncrementerAndComparator;
    }

    public boolean incrementAndUpdate(K key) {
        // Get the existing val if it exists
        V toUpdate = map.get(key);

        // If it doesn't exist, make a new one
        if (toUpdate == null) {
            toUpdate = objectCreatorIncrementerAndComparator.newObj(key);
            map.put(key, toUpdate);
        }

        // Increment toUpdate
        objectCreatorIncrementerAndComparator.increment(toUpdate);

        // Remove element from top list if it is -- to be reinserted at correct pos
        if (array.contains(toUpdate)) {
            array.remove(toUpdate);
        }

        // Loop through values and insert toUpdate
        for (int i = 0; i < maxLength; i++) {

            // If there is a blank space, insert toUpdate
            if (i >= array.size()) {
                array.add(toUpdate);
                return true;
            }
            // Assuming there is an element now, insert if toUpdate is greater
            else if (objectCreatorIncrementerAndComparator.compare(toUpdate, array.get(i))) {

                // Insert toUpdate before i_th elem
                array.add(i, toUpdate);

                //Remove element at the end if the length exceeds maxlength
                if (array.size() > maxLength) {
                    array.remove(maxLength-1);
                }

                return true;
            }
        }

        // Value is not part of the top maxLength
        return false;
    }

	// Return top i_th element
    public V get(int i) {
        return array.get(i);
    }

	// Size of array of top elements. Will be in range 0 to maxLength, inclusive
    public int size() {
        return array.size();
    }
}
