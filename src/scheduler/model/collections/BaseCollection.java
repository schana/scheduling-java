package scheduler.model.collections;

import scheduler.model.BaseItem;

import java.util.*;

public abstract class BaseCollection<T extends BaseItem<T>> implements Iterable<T> {
    protected HashMap<Long, T> map;
    protected ArrayList<T> list;
    protected Random rand = new Random();

    public BaseCollection() {
        map = new HashMap<>();
        list = new ArrayList<>();
    }

    public T get(long id) {
        return map.get(id);
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public void clear() {
        map.clear();
        list.clear();
    }

    public T remove(long id) {
        T item = map.remove(id);
        list.remove(item);
        return item;
    }

    public T put(T t) {
        if (t != null) {
            list.add(t);
            return map.put(t.getId(), t);
        } else {
            return null;
        }
    }

    public boolean contains(T t) {
        return map.containsValue(t);
    }

    public boolean containsKey(long id) {
        return map.containsKey(id);
    }

    public int size() {
        return map.size();
    }

    public T getRandom() {
        if (this.isEmpty()) {
            return null;
        }
        int position = rand.nextInt(this.size());
        return list.get(position);
    }

    public T first() {
        if (this.isEmpty()) {
            return null;
        }
        Collections.sort(list);
        return list.get(0);
    }

    public T last() {
        if (this.isEmpty()) {
            return null;
        }
        Collections.sort(list);
        return list.get(size() - 1);
    }

    @Override
    public Iterator<T> iterator() {
        Collections.sort(list);
        return list.iterator();
    }

    @Override
    public String toString() {
        String toReturn = "";
        for (T t : this) {
            toReturn += t + "\n";
        }
        return toReturn;
    }
}
