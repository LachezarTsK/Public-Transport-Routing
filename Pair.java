package publicTransportRouting;

public class Pair<K, V> {

    public K key;//Station
    V value;//Time from neighbour

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return this.key + ": " + this.value;
    }
}
