package example;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {


    private static final Map<DayOfWeek, List<Pair>> periods = new HashMap<>();

    public static void main(String[] args) throws IOException {

        periods.put(DayOfWeek.MONDAY, Arrays.asList(
                new Pair(new Hour(11,45), new Hour(13,15))));

        periods.put(DayOfWeek.TUESDAY, Arrays.asList(
                new Pair(new Hour(8,0), new Hour(9,30)),
                new Pair(new Hour(17,15), new Hour(19,45))));

        periods.put(DayOfWeek.WEDNESDAY, Arrays.asList(
                new Pair(new Hour(8,15), new Hour(9,45)),
                new Pair(new Hour(11,45), new Hour(13,15)),
                new Pair(new Hour(13,45), new Hour(15,15))));

        periods.put(DayOfWeek.THURSDAY, Arrays.asList(
                new Pair(new Hour(11,45), new Hour(13,15)),
                new Pair(new Hour(13,45), new Hour(15,15))));

        periods.put(DayOfWeek.FRIDAY, Arrays.asList(
                new Pair(new Hour(8,15), new Hour(9,45)),
                new Pair(new Hour(11,45), new Hour(13,15)),
                new Pair(new Hour(13,45), new Hour(15,15)),
                new Pair(new Hour(15,30), new Hour(17,0))));


        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            //TODO rób coś ;
            DayOfWeek today =  DayOfWeek.from(LocalDate.now());
            boolean pause = true;
            for (Pair pair : periods.get(today)) {
                Hour now = new Hour(LocalTime.now().getHour(), LocalTime.now().getMinute());
                if (now.greater(pair.getFrom()) && now.lesser(pair.getTo())) {
                    System.out.println(now.minutesDiff(pair.getTo()) + " minut do końca zajęć!");
                    pause = false;
                    break;
                }
            }
            if (pause){System.out.println("Aktualnie masz przerwę!");}
        }, 0, 60, SECONDS);

        // Validator SQL
        String Query; // tresc zapytania
        String NrEx; // numer zadania
        int stop = 1;

        Scanner ReadNrEx = new Scanner(System.in);
        Scanner ReadQuery = new Scanner(System.in);
        Scanner ReadStop = new Scanner(System.in);



        SqlValidator sqlValidator = new SqlValidator();
        TreeMap<String, String> map = new TreeMap<String, String>();

        ScheduledExecutorService scheduler2 = Executors.newScheduledThreadPool(1);
        int finalStop = stop;
        scheduler2.scheduleAtFixedRate(() -> {
            if (finalStop != 0) {
                Files file = new Files();
                file.DeleteFile("Odp.txt");
                Set<Map.Entry<String, String>> entrySet = map.entrySet();
                for (Map.Entry<String, String> entry : entrySet) {
                    SaveFile savefile = new SaveFile();
                    try {
                        savefile.save(entry.getKey(), entry.getValue());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 30, SECONDS);

        while(stop != 0){
            System.out.println("Podaj numer zadania!");
            NrEx = ReadNrEx.nextLine();
            System.out.println("Wprowadź odpowiedź (Zapytanie SQL)!");
            Query = ReadQuery.nextLine();

            if (sqlValidator.validate(Query) == true){
                map.put(NrEx, Query);


            }else{
                System.out.println("Nieprawidłowa składnia SQL!");
            }
            System.out.println("Chcesz kontynuować? (1 - Tak / 0 - Nie)");
            stop = ReadStop.nextInt();


        }
        //if(stop == 0){
        Files file2 = new Files();
        file2.DeleteFile("Odp.txt");
        Set<Map.Entry<String,String>> entrySet = map.entrySet();
        for(Map.Entry<String, String> entry: entrySet) {
            SaveFile savefile = new SaveFile();
            savefile.save(entry.getKey(), entry.getValue());
            // System.out.println(entry.getKey() + "  " + entry.getValue());

        }
    }

}

class Pair {
    private Hour from;
    private Hour to;

    public Pair(Hour from, Hour to) {
        this.from = from;
        this.to = to;
    }

    public Hour getFrom() {
        return from;
    }

    public void setFrom(Hour from) {
        this.from = from;
    }

    public Hour getTo() {
        return to;
    }

    public void setTo(Hour to) {
        this.to = to;
    }

}

class Hour {
    int h;
    int m;


    public Hour(int h, int m) {
        this.h = h;
        this.m = m;

    }

    public int minutesDiff(Hour hour) {
        //TODO zwróć ile minut różnicy
        int currentHour = LocalTime.now().getHour() ;
        int currentMinute = LocalTime.now().getMinute();
        int result;

        int part1 = (hour.h*60) + hour.m;
        int part2 = (currentHour*60) + currentMinute;
        result = part1 - part2;

        return result;
    }

    public boolean greater(Hour hour) {

        if(this.h > hour.h || (this.h == hour.h && this.m > hour.m)){
            return true;
        }else{
            return false;
        }
    }

    public boolean lesser(Hour hour) {

        if(this.h < hour.h || (this.h == hour.h && this.m < hour.m)){
            return true;
        }else{
            return false;
        }
    }
}

class Files{
    public static void DeleteFile(String path)
    {
        try{

            File file = new File(path);
            file.delete();
            /*if(file.delete()){

            }else{
                System.out.println("Operacja kasowania sie nie powiodla.");
            }*/

        }catch(Exception e){

            e.printStackTrace();

        }
    }
}