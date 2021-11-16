import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class findCpu{
    public static int mount = 0; // 记录数量
    public static void main(String[] args)
    {
        String filename = "C:\\Users\\46088\\Downloads\\Code_Opensource2\\kernel\\arch\\arm\\boot\\dts";
        //创建一个 File 实例，表示路径名是指定路径参数的文件
        File file = new File(filename);
        args=new String[]{"cpu-supply"};//
        for (int i = 0; i < args.length; i++) {
            findFile(file, args[i]);
            print(args[i]);
        }

    }
    public static boolean isTrueFile(File file)
    {
        if(!file.exists() || !file.canRead())
            return false;
        if (file.getName().startsWith("."))
            return false;
        if (file.getName().endsWith("."))
            return false;
        return true;
    }
    public static void findFile(File file, String word)
    {
        File[] listFiles = file.listFiles();
        //得到一个File数组，它默认是按文件最后修改日期排序的
        for (int i = 0; i < listFiles.length; i++)
        {
            if (listFiles[i].isDirectory())
                findFile(listFiles[i], word);
            else if (isTrueFile(listFiles[i]))
                search(listFiles[i], word);
        }
    }
    public static void search(File file, String word)
    {
        try
        {
            int j = 0, k = 0, ch = 0;
            String str = null;
            FileReader in = new FileReader(file);
            while ((ch = in.read()) != -1)
            {
                str += (char) ch;
            }
            if (str != null)
            {
                while (str.indexOf(word, j) != -1)
                {
                    k++;
                    j = str.indexOf(word, j) + 1; // 返回第一次出现的指定子字符串在此字符串中的索引
                }
            }
            if (k > 0)
            {
                System.out.println("在" + file.getAbsolutePath() + "有    " + k+ " 个关键字" + word);
                mount++;
            }
            in.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void print(String word)
    {
        if (mount != 0)
        {
            System.out.println("一共找到    " + mount + " 个文件包含关键字" + word + "! \n");
            mount=0;
        }
        else
        {
            System.out.println("没有找到相应的文件");
        }
    }
}