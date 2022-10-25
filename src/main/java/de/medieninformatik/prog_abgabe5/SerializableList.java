package de.medieninformatik.prog_abgabe5;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Erlaubt die Serialisierung/Deserialisierung von Elementen in einer {@link ArrayList}.
 *
 * @author Elisa Johanna Woelk (m30192)
 * @version 1.0
 * @param <T> Der generische Typ, welcher für die Serialisierung/Deserialisierung verwendet wird
 */
public class SerializableList<T> implements List<T> {

    /**
     * Die {@link ArrayList}, welche für die Instanz der Klasse verwendet wird
     */
    private final ArrayList<T> list;

    /**
     * Der Konstruktor der Klasse, welcher die übergebene {@link ArrayList} auf das Klassenfeld {@link #list} setzt
     * @param list Die {@link ArrayList}, welche für diese Klasseninstanz verwendet werden soll
     */
    public SerializableList(ArrayList<T> list) {
        this.list = list;
    }

    /**
     * Serialisiert den Inhalt der übergebenen {@link List} und schreibt diesen in einen {@link ObjectOutputStream}
     * @param list Die übergebene {@link List} mit dem zu serialisierenden Inhalt
     * @param objectOut Der {@link ObjectOutputStream}, in welchen der Listeninhalt geschrieben werden soll
     * @throws IOException Sollte ein I/O Fehler während der Erstellung des {@link ObjectOutputStream}s auftreten
     */
    public static void serialize(List<?> list, ObjectOutputStream objectOut) throws IOException {

        if (objectOut == null) throw new NullPointerException("ObjectOutputStream is null!");
        if (list == null) throw new NullPointerException("List is null!");

        if (list.isEmpty()) {
            objectOut.writeObject(new ArrayList<>());
        }
        if (list.get(0) instanceof Serializable) {
            objectOut.writeObject(list);
        }
    }

    /**
     * Deserialisiert den Inhalt des übergebenen {@link ObjectInputStream}s und schreibt diesen in eine {@link List}
     * @param elemClass Die Klasse der, im {@link ObjectInputStream} enthaltenen Elemente
     * @param objectInput Der {@link ObjectInputStream} mit den zu deserialisierenden Elementen
     * @return Eine {@link List} mit den deserialisierten Elementen
     * @param <T> Der generische Typ T, welcher für die Serialisierung/Deserialisierung verwendet wird
     */
    public static <T> List<T> deserialize(Class<? extends T> elemClass, ObjectInputStream objectInput) {
        try (objectInput) {
            ArrayList<T> deList = new ArrayList<>();
            ((ArrayList<?>) objectInput.readObject()).forEach(obj -> deList.add(elemClass.cast(obj)));

            return deList;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Increases the capacity of this {@code ArrayList} instance, if
     * necessary, to ensure that it can hold at least the number of elements
     * specified by the minimum capacity argument.
     *
     * @param minCapacity the desired minimum capacity
     */
    public void ensureCapacity(int minCapacity) {
        list.ensureCapacity(minCapacity);
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * Returns {@code true} if this list contains no elements.
     *
     * @return {@code true} if this list contains no elements
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Returns {@code true} if this list contains the specified element.
     * More formally, returns {@code true} if and only if this list contains
     * at least one element {@code e} such that
     * {@code Objects.equals(o, e)}.
     *
     * @param o element whose presence in this list is to be tested
     * @return {@code true} if this list contains the specified element
     */
    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the lowest index {@code i} such that
     * {@code Objects.equals(o, get(i))},
     * or -1 if there is no such index.
     *
     * @param o The object being searched for in the list
     */
    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    /**
     * Returns the index of the last occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the highest index {@code i} such that
     * {@code Objects.equals(o, get(i))},
     * or -1 if there is no such index.
     *
     * @param o The object which last occurrence is to be found in the list
     */
    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    /**
     * Returns a shallow copy of this {@code ArrayList} instance.  (The
     * elements themselves are not copied.)
     *
     * @return a clone of this {@code ArrayList} instance
     */
    @Override
    public Object clone() {
        return list.clone();
    }

    /**
     * Returns an array containing all the elements in this list
     * in proper sequence (from first to last element).
     *
     * <p>The returned array will be "safe" in that no references to it are
     * maintained by this list.  (In other words, this method must allocate
     * a new array).  The caller is thus free to modify the returned array.
     *
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * @return an array containing all the elements in this list in
     * proper sequence
     */
    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    /**
     * Returns an array containing all the elements in this list in proper
     * sequence (from first to last element); the runtime type of the returned
     * array is that of the specified array.  If the list fits in the
     * specified array, it is returned therein.  Otherwise, a new array is
     * allocated with the runtime type of the specified array and the size of
     * this list.
     *
     * <p>If the list fits in the specified array with room to spare
     * (i.e., the array has more elements than the list), the element in
     * the array immediately following the end of the collection is set to
     * {@code null}.  (This is useful in determining the length of the
     * list <i>only</i> if the caller knows that the list does not contain
     * any null elements.)
     *
     * @param a the array into which the elements of the list are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose.
     * @return an array containing the elements of the list
     * @throws ArrayStoreException  if the runtime type of the specified array
     *                              is not a supertype of the runtime type of every element in
     *                              this list
     * @throws NullPointerException if the specified array is null
     */
    @Override
    public <T1> T1[] toArray(T1[] a) {
        return list.toArray(a);
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public T get(int index) {
        return list.get(index);
    }

    /**
     * Replaces the element at the specified position in this list with
     * the specified element.
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public T set(int index, T element) {
        return list.set(index, element);
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param t element to be appended to this list
     * @return {@code true} (as specified by {@link Collection#add})
     */
    @Override
    public boolean add(T t) {
        return list.add(t);
    }

    /**
     * Inserts the specified element at the specified position in this
     * list. Shifts the element currently at that position (if any) and
     * any subsequent elements to the right (adds one to their indices).
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void add(int index, T element) {
        list.add(index, element);
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public T remove(int index) {
        return list.remove(index);
    }

    /**
     * {@inheritDoc}
     *
     * @param o
     */
    @Override
    public boolean equals(Object o) {
        assert o instanceof List<?>;
        return list.equals(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return list.hashCode();
    }

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present.  If the list does not contain the element, it is
     * unchanged.  More formally, removes the element with the lowest index
     * {@code i} such that
     * {@code Objects.equals(o, get(i))}
     * (if such an element exists).  Returns {@code true} if this list
     * contained the specified element (or equivalently, if this list
     * changed as a result of the call).
     *
     * @param o element to be removed from this list, if present
     * @return {@code true} if this list contained the specified element
     */
    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    /**
     * Returns {@code true} if this list contains all the elements of the
     * specified collection.
     *
     * @param c collection to be checked for containment in this list
     * @return {@code true} if this list contains all the elements of the
     * specified collection
     * @throws ClassCastException   if the types of one or more elements
     *                              in the specified collection are incompatible with this
     *                              list
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified collection contains one
     *                              or more null elements and this list does not permit null
     *                              elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>),
     *                              or if the specified collection is null
     * @see #contains(Object)
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    /**
     * Removes all the elements from this list.  The list will
     * be empty after this call returns.
     */
    @Override
    public void clear() {
        list.clear();
    }

    /**
     * Appends all the elements in the specified collection to the end of
     * this list, in the order that they are returned by the
     * specified collection's Iterator.  The behavior of this operation is
     * undefined if the specified collection is modified while the operation
     * is in progress.  (This implies that the behavior of this call is
     * undefined if the specified collection is this list, and this
     * list is nonempty.)
     *
     * @param c collection containing elements to be added to this list
     * @return {@code true} if this list changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        return list.addAll(c);
    }

    /**
     * Inserts all the elements in the specified collection into this
     * list, starting at the specified position.  Shifts the element
     * currently at that position (if any) and any subsequent elements to
     * the right (increases their indices).  The new elements will appear
     * in the list in the order that they are returned by the
     * specified collection's iterator.
     *
     * @param index index at which to insert the first element from the
     *              specified collection
     * @param c     collection containing elements to be added to this list
     * @return {@code true} if this list changed as a result of the call
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @throws NullPointerException      if the specified collection is null
     */
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return list.addAll(index, c);
    }

    /**
     * Removes from this list all of its elements that are contained in the
     * specified collection.
     *
     * @param c collection containing elements to be removed from this list
     * @return {@code true} if this list changed as a result of the call
     * @throws ClassCastException   if the class of an element of this list
     *                              is incompatible with the specified collection
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if this list contains a null element and the
     *                              specified collection does not permit null elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>),
     *                              or if the specified collection is null
     * @see Collection#contains(Object)
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    /**
     * Retains only the elements in this list that are contained in the
     * specified collection.  In other words, removes from this list all
     * of its elements that are not contained in the specified collection.
     *
     * @param c collection containing elements to be retained in this list
     * @return {@code true} if this list changed as a result of the call
     * @throws ClassCastException   if the class of an element of this list
     *                              is incompatible with the specified collection
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if this list contains a null element and the
     *                              specified collection does not permit null elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>),
     *                              or if the specified collection is null
     * @see Collection#contains(Object)
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence), starting at the specified position in the list.
     * The specified index indicates the first element that would be
     * returned by an initial call to {@link ListIterator#next next}.
     * An initial call to {@link ListIterator#previous previous} would
     * return the element with the specified index minus one.
     *
     * <p>The returned list iterator is <a href="#fail-fast"><i>fail-fast</i></a>.
     *
     * @param index The index to be started at
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public ListIterator<T> listIterator(int index) {
        return list.listIterator(index);
    }

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence).
     *
     * <p>The returned list iterator is <a href="#fail-fast"><i>fail-fast</i></a>.
     *
     * @see #listIterator(int)
     */
    @Override
    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * <p>The returned iterator is <a href="#fail-fast"><i>fail-fast</i></a>.
     *
     * @return an iterator over the elements in this list in proper sequence
     */
    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    /**
     * Returns a view of the portion of this list between the specified
     * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.  (If
     * {@code fromIndex} and {@code toIndex} are equal, the returned list is
     * empty.)  The returned list is backed by this list, so non-structural
     * changes in the returned list are reflected in this list, and vice-versa.
     * The returned list supports all the optional list operations.
     *
     * <p>This method eliminates the need for explicit range operations (of
     * the sort that commonly exist for arrays).  Any operation that expects
     * a list can be used as a range operation by passing a subList view
     * instead of a whole list.  For example, the following idiom
     * removes a range of elements from a list:
     * <pre>
     *      list.subList(from, to).clear();
     * </pre>
     * Similar idioms may be constructed for {@link #indexOf(Object)} and
     * {@link #lastIndexOf(Object)}, and all the algorithms in the
     * {@link Collections} class can be applied to a subList.
     *
     * <p>The semantics of the list returned by this method become undefined if
     * the backing list (i.e., this list) is <i>structurally modified</i> in
     * any way other than via the returned list.  (Structural modifications are
     * those that change the size of this list, or otherwise perturb it in such
     * a fashion that iterations in progress may yield incorrect results.)
     *
     * @param fromIndex The starting index for the sublist
     * @param toIndex The ending index of the sublist
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @throws IllegalArgumentException  {@inheritDoc}
     */
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    /**
     * @param action The action to be performed
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    public void forEach(Consumer<? super T> action) {
        list.forEach(action);
    }

    /**
     * Creates a <em><a href="Spliterator.html#binding">late-binding</a></em>
     * and <em>fail-fast</em> {@link Spliterator} over the elements in this
     * list.
     *
     * <p>The {@code Spliterator} reports {@link Spliterator#SIZED},
     * {@link Spliterator#SUBSIZED}, and {@link Spliterator#ORDERED}.
     * Overriding implementations should document the reporting of additional
     * characteristic values.
     *
     * @return a {@code Spliterator} over the elements in this list
     * @since 1.8
     */
    @Override
    public Spliterator<T> spliterator() {
        return list.spliterator();
    }

    /**
     * @param filter The filter used for removing elements
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        return list.removeIf(filter);
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        list.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super T> c) {
        list.sort(c);
    }

    /**
     * Returns an array containing all the elements in this collection,
     * using the provided {@code generator} function to allocate the returned array.
     *
     * <p>If this collection makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the elements in
     * the same order.
     *
     * @param generator a function which produces a new array of the desired
     *                  type and the provided length
     * @return an array containing all the elements in this collection
     * @throws ArrayStoreException  if the runtime type of any element in this
     *                              collection is not assignable to the {@linkplain Class#getComponentType
     *                              runtime component type} of the generated array
     * @throws NullPointerException if the generator function is null
     * @apiNote This method acts as a bridge between array-based and collection-based APIs.
     * It allows creation of an array of a particular runtime type. Use
     * {@link #toArray()} to create an array whose runtime type is {@code Object[]},
     * or use {@link #toArray(Object[]) toArray(T[])} to reuse an existing array.
     *
     * <p>Suppose {@code x} is a collection known to contain only strings.
     * The following code can be used to dump the collection into a newly
     * allocated array of {@code String}:
     *
     * <pre>
     *     String[] y = x.toArray(String[]::new);</pre>
     * @implSpec The default implementation calls the generator function with zero
     * and then passes the resulting array to {@link #toArray(Object[]) toArray(T[])}.
     * @since 11
     */
    @Override
    public <T1> T1[] toArray(IntFunction<T1[]> generator) {
        return list.toArray(generator);
    }

    /**
     * Returns a sequential {@code Stream} with this collection as its source.
     *
     * <p>This method should be overridden when the {@link #spliterator()}
     * method cannot return a spliterator that is {@code IMMUTABLE},
     * {@code CONCURRENT}, or <em>late-binding</em>. (See {@link #spliterator()}
     * for details.)
     *
     * @return a sequential {@code Stream} over the elements in this collection
     * @implSpec The default implementation creates a sequential {@code Stream} from the
     * collection's {@code Spliterator}.
     * @since 1.8
     */
    @Override
    public Stream<T> stream() {
        return list.stream();
    }

    /**
     * Returns a possibly parallel {@code Stream} with this collection as its
     * source.  It is allowable for this method to return a sequential stream.
     *
     * <p>This method should be overridden when the {@link #spliterator()}
     * method cannot return a spliterator that is {@code IMMUTABLE},
     * {@code CONCURRENT}, or <em>late-binding</em>. (See {@link #spliterator()}
     * for details.)
     *
     * @return a possibly parallel {@code Stream} over the elements in this
     * collection
     * @implSpec The default implementation creates a parallel {@code Stream} from the
     * collection's {@code Spliterator}.
     * @since 1.8
     */
    @Override
    public Stream<T> parallelStream() {
        return list.parallelStream();
    }
}
