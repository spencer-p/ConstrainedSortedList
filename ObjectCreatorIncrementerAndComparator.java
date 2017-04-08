/**
 * Created by Spencer Peterson on 4/7/17.
 */

// Creates, Increments, and Compares an object O with a field K
public interface ObjectCreatorIncrementerAndComparator<K,O> {
    // Create new object
    public O newObj(K key);

    // Increment the object
    public void increment(O obj);

    // Return true if obj1 is greater than obj2
    public boolean compare(O obj1, O obj2);
}
