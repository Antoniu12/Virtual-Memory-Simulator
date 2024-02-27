import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VirtualMemory {
    private HashMap<String, Page> pageMap;
    private List<String> pageOrder;

    public VirtualMemory() {
        pageMap = new HashMap<>(10);
        pageOrder = new ArrayList<>();
    }

    public HashMap getPageOrder(){
        return this.pageMap;
    }
    public boolean pageExists(String nume) {
        return pageMap.containsKey(nume);
    }

    public void push(Page page) {
        String nume = page.getNume();
        pageMap.put(nume, page);
        pageOrder.add(nume);
    }

    public Page pop() {
        if (!pageOrder.isEmpty()) {
            String oldestPageName = pageOrder.remove(0);
            Page oldestPage = pageMap.remove(oldestPageName);
            List<String> text = new ArrayList<>();
            try {
                BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Anto\\Documents\\Faculta\\An3\\Sem1\\SSC\\ssc_proiect\\src\\DiscMem.txt"));
                String line=null;
                while ((line = reader.readLine()) != null) {
                    text.add(line);
                }
                reader.close();

                BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Anto\\Documents\\Faculta\\An3\\Sem1\\SSC\\ssc_proiect\\src\\DiscMem.txt"));
                for (String lines : text) {
                    writer.write(lines);
                    writer.newLine();
                }
                writer.write(oldestPageName);
                writer.close();
            } catch (IOException e) {
                System.err.println("Eroare la citire din disc: " + e.getMessage());
            }

            return oldestPage;
        }
        return null;
    }

    public void createPage(String nume) {
        if ( pageExistsInFile(nume)) {
            if(pageMap.size()<10) {
                Page page = new Page(nume);
                push(page);
            }
            else {
                System.out.println("Memoria Virtuala este plina!");
            }
        } else {
            System.out.println("Pagina " + nume + " nu exista pe disc!");
        }
    }

    public void replace(String nume, String nume2) throws IOException {
        Page newPage;
        Page oldPage;
        if (pageExists(nume)) {
            if (pageExistsInFileWithoutRemoving(nume2)) {
                List<String> text = new ArrayList<>();

                try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Anto\\Documents\\Faculta\\An3\\Sem1\\SSC\\ssc_proiect\\src\\DiscMem.txt"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if(!line.trim().equals(nume2)){
                            text.add(line);
                        } else {
                            text.add(nume);
                        }
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Anto\\Documents\\Faculta\\An3\\Sem1\\SSC\\ssc_proiect\\src\\DiscMem.txt"))) {
                    for (String lines : text) {
                        writer.write(lines);
                        writer.newLine();
                    }
                }
                int poz = pageOrder.indexOf(nume);
                pageOrder.set(poz, nume2);

                newPage = new Page(nume2);
                pageMap.remove(nume);
                pageMap.put(nume2, newPage);
            } else {
                System.out.println("Pagina " + nume2 + " nu exista in memorie!");
            }
        } else {
            System.out.println("Pagina " + nume + " nu exista pe disc!");
        }
    }

    private boolean pageExistsInFile(String nume) {
        List<String> text = new ArrayList<>();
        int ok=0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Anto\\Documents\\Faculta\\An3\\Sem1\\SSC\\ssc_proiect\\src\\DiscMem.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().equals(nume))
                    text.add(line);
                else ok=1;
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Anto\\Documents\\Faculta\\An3\\Sem1\\SSC\\ssc_proiect\\src\\DiscMem.txt"));
            for (String lines : text) {
                writer.write(lines);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Eroare la citire din disc: " + e.getMessage());
        }
        if (ok==1)
            return true;
        return false;
    }

    private boolean pageExistsInFileWithoutRemoving(String nume) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Anto\\Documents\\Faculta\\An3\\Sem1\\SSC\\ssc_proiect\\src\\DiscMem.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals(nume))
                    return true;
            }
            reader.close();

        } catch (IOException e) {
            System.err.println("Error reading from the file: " + e.getMessage());
        }
        return false;
    }

}

