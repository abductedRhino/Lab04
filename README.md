# Lab04
### Assignment 1 - Eight Queens

Our goal is to write a program to determine if 8 queens can be placed on an 8 x 8 chess board without them threatening each other!

### 1.

**Decide how to represent a chess board with a data structure. Don’t worry about the colors of the board yet. Write a Chessboard class. What methods will you need?**

Ein 8x8 Schachbrett ist ein `int[8][8]`.

- Wenn eine Königin auf einem Feld steht, hat es den Wert -1.
- Ein Feld, das von einer oder mehreren Königinnen bedroht wird, hat den Wert 1 oder höher.
- Ein Feld, das nicht bedroht wird und auf dem keine Königin steht, hat den Wert 0.

Wir brauchen eine Methode `addQueen(int row, int column)`, um eine Königin auf das Schachbrett zu stellen. Dabei müssen alle bedrohten Felder geändert werden.

Wir nutzen eine Methode `findQueen(int row)`, die die Königin einer bestimmten Reihe findet.

Außerdem eine Methode `removeQueen(int row)`, um eine Königin zu entfernen und die von ihr bedrohten Felder zu ändern.

Um die laufzeit zu verringern, werden die königinnen in einem `int[] queens` gespeichert. Dabei wird für jede Zeile die Spalte der königin gespeichert.

### 2.

**Write a method for determining if the current board state contains a queen that is threatening another one. If the complexity in terms of N (rows on the board) of your algorithm is worse than linear, try to improve your algorithm. What is the complexity of your final method in terms of N (the number of rows on the board)?**

- Man kann nur so viele Königinnnen auf dem Schachbrett platzieren, wie es spalten/zeilen gibt, weil sonst 2 in der selben spalte stünden.
- Also N(rows on the board) ≤ N(queens on the board)

Unsere `backtrack(row,column)` methode sucht nur freie Felder aus um dort königinnen aufzustellen. Eine Methode mit so kleiner Komplexität haben wir mit unserem `int[][]` nicht geschafft.

### 3.

**We speak of “backtracking” when we go back to a previous state and try a different branch. Use some coins on your paper chess board to figure out what to do when you reach a state in which one queen is threatened by another. There are iterative, recursive, and random solutions to this problem. Try and implement a recursive solution.**

Wir fangen in der obersten Zeile an und unterscheiden die Fälle:

- Feld ist bedroht
    - nach rechts rücken ist nicht mehr möglich
        - die königin in der Zeile darüber kann nach rechts rücken
            
            —> Königin darüber 1 nach rechts rücken.
            
        - die königin in der Zeile darüber kann nicht nach rechts rücken
            - die königin darüber steht nicht in der obersten Zeile
                
                —> Königin 2 Zeilen oberhalb 1 nach rechts rücken
                
            - die königin darüber steht in der obersten Zeile
                
                —> Es gibt keine weiteren Lösungen, Programm beenden.
                
    - nach rechts rücken ist möglich
        
        —> in dieser Zeile Königin nach rechts rücken.
        
- Feld ist nicht bedroht
    - es ist die unterste Zeile
        
        —> Königin Platzieren, Lösung speichern, anschließend verfahren als wäre es keine gültige Lösung um diesen Zweig nicht doppelt zu zählen.
        
    - es ist eine andere Zeile
        
        —> Königin platzieren und mit der nächsten Zeile weiter machen.
```java
		public void backtrack(int row, int column) {
        boolean danger = board[row][column] > 0;
        // no save spot in this row:
        if (column == size - 1 && danger) {
            // if last queen not on edge, move it.
            if (findQueen(row - 1) != size - 1) {
                backtrack(row - 1, removeQueen(row - 1) + 1);
            }
            // if last queen on edge and last row > 0
            else if (row - 1 > 0) {
                removeQueen(row - 1);
                backtrack(row - 2, removeQueen(row - 2) + 1);
            }
            // if last queen on edge and last row == 0
            else if (row - 1 == 0) {
                System.out.println("done.");
                this.done = true;
            }
        }
        // this spot is unsafe, but more columns are available in the same row:
        else if (danger) {
            backtrack(row, column + 1);
        }
        // this spot is safe, and it is the last row:
        else if (row == size - 1) {
            addQueen(row, column);
            solutions.add(this.board);
            System.out.println(solutions.size()+"\n"+this);
            if (column < size - 1) {
                this.r = row;
                this.c = removeQueen(row) + 1;
            } else {
                removeQueen(row);
                this.r = row - 1;
                this.c = removeQueen(row - 1) + 1;
            }
        }
        // this spot is safe, but not last row:
        else {
            addQueen(row, column);
            backtrack(row + 1, 0);
        }
    }
```

### 4.

**Now implement a search routine that looks for a state in which the queens don’t threaten each other. If there is a solution, print it to System.out. If there is more than one solution, print them as well.**

![Untitled2](https://github.com/abductedRhino/Lab04/blob/animations/Screenshots/Untitled%202.png)
![Untitled1](https://github.com/abductedRhino/Lab04/blob/animations/Screenshots/Untitled%201.png) 
![Untitled](https://github.com/abductedRhino/Lab04/blob/animations/Screenshots/Untitled.png) 

Jedes Mal, wenn eine Lösung gefunden wird, wird sie in die Konsole gedruckt. Anschließend wird geht der Algorithmus weiter, als wäre die Lösung nicht gültig. 

```java
				// this spot is safe, and it is the last row:
        else if (row == size - 1) {
            addQueen(row, column);
            solutions.add(this.board);
            System.out.println(solutions.size()+"\n"+this);
            if (column < size - 1) {
                this.r = row;
                this.c = removeQueen(row) + 1;
            } else {
                removeQueen(row);
                this.r = row - 1;
                this.c = removeQueen(row - 1) + 1;
            }
        }
```

Auf diese Weise geht es so lange weiter, bis die Königin der obersten Zeile dazu aufgefordert wird, eins nach rechts zu rücken, das aber nicht mehr möglich ist. Das funktioniert, weil sie nur dann dazu aufgefordert wird, wenn die unteren Königinnen keinen unbedrohten platz mehr finden:

```java
				// no save spot in this row:
        if (column == size - 1 && danger) {
						// (...)
            // if last queen on edge and last row == 0
            else if (row - 1 == 0) {
                System.out.println("done.");
                this.done = true;
            }
        }
```

### 5.

**Does your program work for 10 queens on a 10 x 10 board? 13 on a 13 x 13 board?**

Nein, es funktioniert nur bis 9x9 für alle 352 Lösungen. Dannach gibt es einen StackOverflowError. Bei Eingaben größer als 9 für `size` wird daher nur die erste Lösung ermittelt. Für die GUI funktioniert es allerdings.

13x13:

![Untitled3](https://github.com/abductedRhino/Lab04/blob/animations/Screenshots/Untitled%203.png) 
![Untitled4](https://github.com/abductedRhino/Lab04/blob/animations/Screenshots/Untitled%204.png)

### 6.

**Design a Chessboard GUI with a queen figure. Output the result of the program using your Chessboard GUI.**

Wir benutzen die erste gefundene Lösung um die Königinnen darzustellen. Um es einfacher zu machen, kann man die größe nicht verändern. Wir überschreiben die paint() Methode und implementieren unsere eigene. Darin wird unsere Methode `drawChessBoard()` aufgerufen, die erst ein in einem loop `fillRectangle` ausführt und dabei die Farbe wechselt. In jeder Zeile wird `row%2==0` überprüft und danach die Farbe der ersten Spalte ausgesucht. So funktioniert die GUI auch für ungerade Schachbrettgrößen.

```java
private void drawChessBoard(Graphics2D g) {
        int fieldSize = (int) Math.round((double) panel.getHeight() / this.size);
        Point upperLeft = new Point(0, 0);
        Color lightField = Color.LIGHT_GRAY;
        Color darkField = Color.DARK_GRAY;
        for (int row = upperLeft.y, i = 0; row < panel.getHeight(); row += fieldSize, i++) {
            g.setColor(i % 2 == 0 ? lightField : darkField);
            for (int col = upperLeft.x; col < panel.getHeight(); col += fieldSize) {
                g.fillRect(col, row, fieldSize, fieldSize);
                g.setColor(g.getColor() == lightField ? darkField : lightField);
            }
        }
        g.setColor(Color.CYAN);
        int queenSize = fieldSize / 2;
        int offset = queenSize / 2;
        for (int i = 0; i < queens.length; i++) {
            int j = queens[i];
            g.fillOval(offset + (j * fieldSize), offset + (i * fieldSize),queenSize,queenSize);
        }
    }
```

![Untitled5](https://github.com/abductedRhino/Lab04/blob/animations/Screenshots/Untitled%205.png) 
![Untitled6](https://github.com/abductedRhino/Lab04/blob/animations/Screenshots/Untitled%206.png)

### 7.

**Animate the search by showing the positions as they are tested. To achieve this, you could show the board on each step with the positions that are currently being tested, wait a certain ammount of time and then show the next position. Write this from scratch without a scaffold this time.**

Wir haben das mit einem while-loop in der backtrack Methode gelöst, weil wir es nicht mit `wait()` oder Timer geschafft haben.

```java
		public void backtrack(int row, int column) {
        time = System.currentTimeMillis();
        panel.repaint();
        while (System.currentTimeMillis() < time + 30) {

        }
```

---

### Assignment 2 - Sorting

### 1.

**Choose two of the following Algorithms and implement them to be able to sort an int array: selection sort, insertion sort, merge sort, quick sort**

```java
Sorting Test = new Sorting();
       int[] aeh = Test.selectionSort(new int[]{4,7,3,1,6});
       System.out.println(Arrays.toString(aeh));

        int[] oeh = Test.insertionSort(new int[]{4,7,3,1,6});
        System.out.println(Arrays.toString(oeh));
```

```java
public class Sorting {
    public int[] selectionSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int cursor = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[cursor]) {
                    cursor = j;
                }
            }
            int low = a[cursor];
            a[cursor] = a[i];
            a[i] = low;
        }
        return a ;
    }

    public int[] insertionSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int start = a[i];
            int k = i - 1;
            while ((k >= 0) && (a[k] > start)) {
                a[k + 1] = a[k];
                k--;
            }
            a[k + 1] = start;
        }
        return a;
    }
}
```

### 2.

**Test your implementations using JUnit. Make sure your implementations sort an int array correctly and can handle empty and already sorted arrays as well.**

Wir haben die Testcases leeres array, sortiertes array und unsortiertes array benutzt.

```java
Sorting sort = new Sorting();

    @org.junit.jupiter.api.Test
    void selectionSort() {
        int[] test = new int[0];
        assertArrayEquals(test, sort.insertionSort(test));
        test = new int[]{1, 3, 5};
        assertArrayEquals(test, sort.insertionSort(test));
        test = new int[]{0, 0, 0, 0};
        assertArrayEquals(test, sort.insertionSort(test));
        test = new int[]{2, 5, 3, 1};
        assertArrayEquals(new int[]{1, 2, 3, 5}, sort.insertionSort(test));
    }

    @org.junit.jupiter.api.Test
    void insertionSort() {
        int[] test = new int[0];
        assertArrayEquals(test, sort.insertionSort(test));
        test = new int[]{1, 3, 5};
        assertArrayEquals(test, sort.insertionSort(test));
        test = new int[]{0, 0, 0, 0};
        assertArrayEquals(test, sort.insertionSort(test));
        test = new int[]{2, 5, 3, 1};
        assertArrayEquals(new int[]{1, 2, 3, 5}, sort.insertionSort(test));
    }
```
