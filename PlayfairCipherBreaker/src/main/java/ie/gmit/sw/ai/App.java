package ie.gmit.sw.ai;

import ie.gmit.sw.ai.cipher.IKeyGenerator;
import ie.gmit.sw.ai.cipher.KeyGenerator;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Hello World!");
        
        IKeyGenerator keygen = new KeyGenerator();
        String key = keygen.generateKey("ABCDEFGHIKLMNOPQRSTUVWXYZ");
        System.out.println(key);
        System.out.println(key.length());
    }
}
