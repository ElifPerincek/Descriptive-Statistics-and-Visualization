
package descriptive_statistics;

import java.awt.BorderLayout;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class DescriptiveStatisticsTool {

    private static List<Double> degisken1=new ArrayList<>();//1.veri seti için liste
    private static List<Double> degisken2=new ArrayList<>();//2.veri seti için liste
    private static List<Double> degisken3=new ArrayList<>();//3.veri seti için liste
    private static List<Double> degisken4=new ArrayList<>();//4.veri seti için liste
    private static List<List<Double>> tumListeler=Arrays.asList(degisken1,degisken2,degisken3,degisken4);
    public static void main(String[] args) {
        
        DefaultBoxAndWhiskerCategoryDataset veriSeti= new DefaultBoxAndWhiskerCategoryDataset();
        String dosya="Iris.csv";
         try (BufferedReader br = new BufferedReader(new FileReader(dosya))) 
        {
            String satir;
            int okundu=0;
            if((satir=br.readLine())!=null)
            {
                okundu=1;
            }
           
            if(okundu!=0)
            {
                while ((satir = br.readLine()) != null)
                {
                    String[] veriler = satir.split(","); 
                    for(int i=1;i<5;i++)
                    {
                        if(i==1)
                        {
                            degisken1.add(Double.parseDouble(veriler[i].trim()));
                       }
                        else if(i==2)
                        {
                            degisken2.add(Double.parseDouble(veriler[i].trim()));
                        }
                         else if(i==3)
                        {
                            degisken3.add(Double.parseDouble(veriler[i].trim()));
                        }
                        else
                        {
                            degisken4.add(Double.parseDouble(veriler[i].trim()));
                        }
                    }
                }     
            }
            veriSeti.add(degisken1,"CanakYaprakUzunlugu(cm)","KategoriA");
            veriSeti.add(degisken2,"CanakYaprakGenisligi(cm)","KategoriB");
            veriSeti.add(degisken3,"TacYaprakUzunlugu(cm)","KategoriC");
            veriSeti.add(degisken4,"TacYaprakGenisligi(cm)","KategoriD");
            br.close();
            String dosyaYaz="sonuc.txt";
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaYaz)))
            {
                char sayac='A';
                 for(List<Double> list:tumListeler)
                {
                    writer.write("Kategori " + sayac + " Özellikleri:\n");
                    System.out.println("Kategori "+sayac+" Ozellikleri: ");
                    ozellikleriYazdir(list,writer);
                    sayac++;
                    writer.write("\n");
                    System.out.println();
                }
                 writer.close();
            }
            catch(IOException e)
            {
                System.out.println("Dosyaya Yazilirken Hata Olustu: "+e.getMessage());
            }
        } 
        catch (IOException | NumberFormatException e) 
        {
            e.printStackTrace();
        }
        
        


        JFreeChart chart = ChartFactory.createBoxAndWhiskerChart(
            "Boxplot", 
            "Kategoriler",    
            "Değerler",      
            veriSeti,          
            true
        );
        


        
        JFrame frame = new JFrame("Boxplot Örneği");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setVisible(true);
        
    }
    public static void ozellikleriYazdir(List<Double> list,BufferedWriter writer) throws IOException
    {
        
        List<Double> aykiriDeger=aykiriDegerleriBul(list);
        if(aykiriDeger.isEmpty())
        {
            System.out.println("Degiskenin aykiri degeri yoktur!");
            writer.write("Aykiri deger yoktur!\n");
        }
        else
        {
            System.out.println("Aykiri Degerler: ");
            writer.write("Aykiri Degerler:\n");
            for(double veri:aykiriDeger)
            {
                String deger=String.valueOf(veri);
                writer.write(deger);
                writer.write("\n");
                System.out.println(veri);
            }
            aykiriDegerleriVeriSetindenCikar(list);
        }
        String aritmetikOrtalama="Aritmetik Ortalama: "+aritmetikOrtalamaHesaplama(list);
        String medyan = "Ortanca(Medyan): " +medyanOrtancaHesaplama(list);
        String mod = "Tepe Deger(Mod): " + tepeDegerModHesaplama(list);
        String degisimAraligi = "Degisim Araligi: " + degisimAraligiHesaplama(list);
        String ortalamaMutlakSapma = "Ortalama Mutlak Sapma: " + ortalamaMutlakSapmaHesaplama(list);
        String varyans = "Varyans: " + orneklemVaryansHesaplama(list);
        String standartSapma = "Standart Sapma: " + orneklemStandartSapmaHesaplama(list);
        String degisimKatsayisi="Degisim Katsayisi: %"+orneklemDegisimKatsayisiHesaplama(list);
        String ceyreklerAcikligi="Ceyrekler Acikligi: "+ceyreklerAcikligiHesaplama(list);
        
        System.out.println(aritmetikOrtalama);
        System.out.println(medyan);
        System.out.println(mod);
        System.out.println(degisimAraligi);
        System.out.println(ortalamaMutlakSapma);
        System.out.println(varyans);
        System.out.println(standartSapma);
        System.out.println(degisimKatsayisi);
        System.out.println(ceyreklerAcikligi);
        writer.write(aritmetikOrtalama+"\n");
        writer.write(medyan+"\n");
        writer.write(mod+"\n");
        writer.write(degisimAraligi+"\n");
        writer.write(ortalamaMutlakSapma+"\n");
        writer.write(varyans+"\n");
        writer.write(standartSapma+"\n");
        writer.write(degisimKatsayisi+"\n");
        writer.write(ceyreklerAcikligi+"\n");
    }
    public static void quickSort(List<Double> list,int baslangic,int bitis)
    {
        if (baslangic < bitis) {
            int pivotIndex = partition(list,baslangic,bitis);
            quickSort(list,baslangic, pivotIndex - 1);
            quickSort(list, pivotIndex + 1,bitis);
        }
    }
    
    public static int partition(List<Double> list,int baslangic,int bitis)
    {
        double pivot = list.get(bitis);
        int i = baslangic - 1;

        for (int j = baslangic; j < bitis; j++) {
            if (list.get(j) <= pivot) {
                i++;
                double temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }

        double temp = list.get(i + 1);
        list.set(i + 1, list.get(bitis));
        list.set(bitis, temp);

        return i + 1;
    }
    public static double[] ceyrekler(List<Double> list)
    {
        double Q1,Q2,Q3;
        double deger1,deger2,deger3;
        int tamKisim1,tamKisim2,tamKisim3;
        int terim1,terim2,terim3;
        double ondalikKisim1,ondalikKisim2,ondalikKisim3;
        double ceyreklerAcikligi;
        int veriSayisi=list.size();
        
        deger1=(veriSayisi+1)/4;
        tamKisim1=(int)deger1;
        ondalikKisim1=deger1-tamKisim1;
        
        deger2=(veriSayisi+1)/2;
        tamKisim2=(int)deger2;
        ondalikKisim2=deger2-tamKisim2;
        
        deger3=3*(veriSayisi+1)/4;
        tamKisim3=(int)deger3;
        ondalikKisim3=deger3-tamKisim3;
        
        quickSort(list,0,veriSayisi-1);
        if(ondalikKisim1==(0.0))
        {
            Q1=list.get(tamKisim1);
        }
        else
        {
            Q1=(list.get(tamKisim1)+list.get(tamKisim1+1))/2;
        }
        if(ondalikKisim2==(0.0))
        {
            Q2=list.get(tamKisim2);
        }
        else
        {
            Q2=(list.get(tamKisim2)+list.get(tamKisim2+1))/2;
        }
        if(ondalikKisim3==(0.0))
        {
            Q3=list.get(tamKisim3);
        }
        else
        {
            Q3=(list.get(tamKisim3)+list.get(tamKisim3+1))/2;
        }
        double[] dizi={Q1,Q2,Q3};
        return dizi;
    }
    public static double ceyreklerAcikligiHesaplama(List<Double> list)
    {
        double[] dizi=ceyrekler(list);
        double ceyreklerAcikligi=dizi[2]-dizi[0];
        return ceyreklerAcikligi;
    }
    public static List<Double> aykiriDegerleriBul(List<Double> list)
    {
        List<Double> aykiriDegerler = new ArrayList<>();
        double[] ceyrekler=ceyrekler(list);
        double altUcDeger,ustUcDeger;
        double ceyreklerAcikligi=ceyreklerAcikligiHesaplama(list);
        altUcDeger=ceyrekler[0]-(1.5)*ceyreklerAcikligi;
        ustUcDeger=ceyrekler[2]+(1.5)*ceyreklerAcikligi;
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i)<altUcDeger || list.get(i)>ustUcDeger)
            {
                aykiriDegerler.add(list.get(i));
            }
        }
        return aykiriDegerler;
    }
    public static void aykiriDegerleriVeriSetindenCikar(List<Double> list)
    {
        List<Double> aykiriDegerlerListesi=aykiriDegerleriBul(list);
        double[] aykiriDegerler=new double[aykiriDegerlerListesi.size()];
       for(int i=0;i<aykiriDegerlerListesi.size();i++)
       {
           aykiriDegerler[i]=aykiriDegerlerListesi.get(i);
       }
       list.removeIf(deger -> listKontrol(aykiriDegerler,deger));
    }
    public static boolean listKontrol(double[] dizi,double deger)
    {
        for(double eleman:dizi)
        {
            if(eleman==deger)
            {
                return true;
            }
        }
        return false;
    }
    public static double aritmetikOrtalamaHesaplama(List<Double> list)
    {
        double toplam=0;
        double aritmetikOrtalama;
        int veriSayisi=list.size();
        for(double veri:list)
        {
            toplam+=veri;
        }
        aritmetikOrtalama=(double)toplam/veriSayisi;
        return aritmetikOrtalama;
    }
    public static double medyanOrtancaHesaplama(List<Double> list)
    {
        int veriSayisi=list.size();
        quickSort(list,0,veriSayisi-1);
        if(veriSayisi%2!=0)
        {
            int deger=(veriSayisi+1)/2;
            return list.get(deger);
        }
        else
        {
            int deger1=veriSayisi/2;
            return (list.get(deger1)+list.get(deger1+1))/2;
        }
    }
    public static double tepeDegerModHesaplama(List<Double> list)
    {
        List<Double> elemanlar = new ArrayList<>();
        List<Integer> tekrarSayilari = new ArrayList<>();
        for(double eleman:list)
        {
            if(elemanlar.contains(eleman))
            {
                int index=elemanlar.indexOf(eleman);
                tekrarSayilari.set(index,tekrarSayilari.get(index)+1);
            }
            else
            {
                elemanlar.add(eleman);
                tekrarSayilari.add(1);
            }
        }
        int maxTekrar=0;
        double tepeDeger=tekrarSayilari.get(0);
        for(int i=0;i<elemanlar.size();i++)
        {
            if(tekrarSayilari.get(i)>maxTekrar)
            {
                maxTekrar=tekrarSayilari.get(i);
                tepeDeger=elemanlar.get(i);
            }
        }
        return tepeDeger;
    }
    public static double degisimAraligiHesaplama(List<Double> list)
    {
        int veriSayisi=list.size();
        quickSort(list,0,veriSayisi-1);
        double degisimAraligi;
        degisimAraligi=list.get(veriSayisi-1)-list.get(0);
        return degisimAraligi;
    }
    public static double ortalamaMutlakSapmaHesaplama(List<Double> list)
    {
        int veriSayisi=list.size();
        double aritmetikOrtalama=aritmetikOrtalamaHesaplama(list);
        double toplam=0;
        double ortalamaMutlakSapma;
        for(double veri:list)
        {
            toplam+=Math.abs(veri-aritmetikOrtalama);
        }
       ortalamaMutlakSapma=(double)toplam/veriSayisi;
       return ortalamaMutlakSapma;
    }
    public static double populasyonVaryansHesaplama(List<Double> list)
    {
        int veriSayisi=list.size();
        double varyans;
        double aritmetikOrtalama=aritmetikOrtalamaHesaplama(list);
        double toplam=0;
        for(double veri:list)
        {
            toplam+=Math.pow(veri-aritmetikOrtalama,2);
        }
        varyans=(double)toplam/veriSayisi;
        return varyans;
    }
    public static double orneklemVaryansHesaplama(List<Double> list)
    {
        int veriSayisi=list.size();
        double varyans;
        double aritmetikOrtalama=aritmetikOrtalamaHesaplama(list);
        double toplam=0;
        for(double veri:list)
        {
            toplam+=Math.pow((veri-aritmetikOrtalama),2);
        }
        varyans=(double)toplam/(veriSayisi-1);
        return varyans;
    }
    public static double populasyonStandartSapmaHesaplama(List<Double> list)
    {
        double varyans=populasyonVaryansHesaplama(list);
        double standartSapma=Math.sqrt(varyans);
        return standartSapma;
    }
    public static double orneklemStandartSapmaHesaplama(List<Double> list)
    {
        double varyans=orneklemVaryansHesaplama(list);
        double standartSapma=Math.sqrt(varyans);
        return standartSapma;
    }
    public static double orneklemDegisimKatsayisiHesaplama(List<Double> list)
    {
        double degisimKatsayisi,standartSapma,aritmetikOrtalama;
        aritmetikOrtalama=aritmetikOrtalamaHesaplama(list);
        standartSapma=orneklemStandartSapmaHesaplama(list);
        degisimKatsayisi=(double)standartSapma/aritmetikOrtalama;
        return degisimKatsayisi;
    }
    public static double populasyonDegisimKatsayisiHesaplama(List<Double> list)
    {
        double degisimKatsayisi,standartSapma,aritmetikOrtalama;
        aritmetikOrtalama=aritmetikOrtalamaHesaplama(list);
        standartSapma=populasyonStandartSapmaHesaplama(list);
        degisimKatsayisi=(double)standartSapma/aritmetikOrtalama;
        return degisimKatsayisi;
    }

}
