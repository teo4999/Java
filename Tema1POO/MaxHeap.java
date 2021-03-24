import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Hossu Teodor-Ioan 325CB
 */

public class MaxHeap {
    private Pasageri[] Heap;
    private int[] vector;
    private int size;
    private int maxsize;
    private PrintWriter printWriter;

    // Constructor to initialize an
    // empty max heap with given maximum
    // capacity.

    public MaxHeap(int maxsize, PrintWriter printWriter) throws IOException {
        this.maxsize = maxsize;
        this.size = 0;
        Heap = new Pasageri[this.maxsize + 1];
        vector = new int[this.maxsize + 1];
        vector[0] = Integer.MAX_VALUE;
        this.printWriter = printWriter;
    }

    // Returns position of parent
    private int parent(int pos)
    {
        return pos / 2;
    }

    // Below two functions return left and
    // right children.
    private int leftChild(int pos)
    {
        return (2 * pos);
    }
    private int rightChild(int pos)
    {
        return (2 * pos) + 1;
    }

    // Returns true of given node is leaf
    private boolean isLeaf(int pos)
    {
        if (pos > (size / 2) && pos <= size) {
            return true;
        }
        return false;
    }

    //Swap 2 elements
    private void swap(int fpos, int spos)
    {
        Pasageri tmp;
        tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;

        int tmp1;
        tmp1 = vector[fpos];
        vector[fpos] = vector[spos];
        vector[spos] = tmp1;
    }

    // A recursive function to max heapify the given
    // subtree. This function assumes that the left and
    // right subtrees are already heapified, we only need
    // to fix the root.
    private void maxHeapify(int pos)
    {
        if (isLeaf(pos))
            return;

        if (vector[pos] < vector[leftChild(pos)] ||
                vector[pos] < vector[rightChild(pos)]) {

            if (vector[leftChild(pos)] >= vector[rightChild(pos)]) {
                swap(pos, leftChild(pos));
                maxHeapify(leftChild(pos));
            }
            else {
                swap(pos, rightChild(pos));
                maxHeapify(rightChild(pos));
            }
        }
    }

    // Inserts a new element to max heap
    public void insert(Pasageri p, int priority)
    {
        Heap[++size] = p;
        vector[size] = priority;
        // Traverse up and fix violated property
        int current = size;
        while (vector[current] > vector[parent(current)]) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public void print(int pos)
    {
        if(Heap[pos] == null)
            return;
        printWriter.print(Heap[pos].id + " ");
        if(leftChild(pos) <= maxsize)
            print(leftChild(pos));
        if(rightChild(pos) <= maxsize)
            print(rightChild(pos));
    }

    // List the max heap
    public void list() {
        print(1);
    }

    // Remove the root from max heap
    public void embark()
    {
        if(size > 0) {
            swap(1, size);
            vector[size] = 0;
            Heap[size] = null;
            size--;
            maxHeapify(1);
        }
    }

    // Remove a single person, a family or a group
    public void delete(Pasageri p) {
        for(int i = 1; i <= size; i++)
            if(Heap[i].equals(p)) {
                swap(1, i);
                embark();
            }
    }

    // Remove a person from a family or a group
    public void delete(Pasageri p, String pers) {
        for(int i = 0; i <= p.index; i++)
            if(p.persoane[i].nume.equals(pers)) {
                Persoana aux = new Persoana();
                aux = p.persoane[i];
                p.persoane[i] = p.persoane[p.index];
                p.persoane[p.index] = aux;
                p.index--;
                for(int j = 1; j <= size; j++)
                    if(Heap[j].id.equals(p.id)) {
                        Heap[j] = p;
                        vector[j] = p.getPrioritate();
                        if (vector[j] < vector[leftChild(j)] ||
                                vector[j] < vector[rightChild(j)]) {

                            if (vector[leftChild(j)] >= vector[rightChild(j)]) {
                                swap(j, leftChild(j));
                                maxHeapify(leftChild(j));
                            }
                            else {
                                swap(j, rightChild(j));
                                maxHeapify(rightChild(j));
                            }
                        }
                    }
                maxHeapify(1);
                break;
            }
    }

    public void closeFile() {
        this.printWriter.close();
    }
}