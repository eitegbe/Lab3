package edu.sjsu.cmpe.cache.client;

import com.google.common.hash.Hashing;

import java.util.ArrayList;

public class Client {


    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");

        //Stores the server nodes in the array list..
        ArrayList<String> serverNodes = new ArrayList<String>();

        //servers
        serverNodes.add("http://localhost:3000");
        serverNodes.add("http://localhost:3002");
        serverNodes.add("http://localhost:3001");

        //Passes the server into the hashing algorithm
        ConsistentHash<String> consistentHash = new ConsistentHash<String>(Hashing.md5(),
                1, serverNodes);
        for (int count = 1, letterCount = 97; count <= 10 && letterCount <= 106; count++, letterCount++) {
            String serverURL = consistentHash.get(count);
            CacheServiceInterface cache = new DistributedCacheService(serverURL);
            cache.put(count, String.valueOf((char) letterCount));

            System.out.println("PUT ==> node "+serverURL);
            System.out.println("put ==>("+count+","+String.valueOf((char) letterCount)+")");

            System.out.println("GET ==> node "+serverURL);
            System.out.println("get(" + count + ") => " + cache.get(count));
        }
        System.out.println();


     /*   //RendezvousHash
        RendezvousHash<String> rendezvousHash = new RendezvousHash<String>(Hashing.md5(),
                1, serverNodes);

        for (int count = 1, letterCount = 97; count <= 10 && letterCount <= 106; count++, letterCount++) {
            String cacheURL = rendezvousHash.get(count);
            CacheServiceInterface cache = new DistributedCacheService(cacheURL);
            cache.put(count, String.valueOf((char) letterCount));

            System.out.println("PUT ==> node "+cacheURL);
            System.out.println("put ==>("+count+","+String.valueOf((char) letterCount)+")");

            System.out.println("GET ==> node "+cacheURL);
            System.out.println("get(" + count + ") => " + cache.get(count));
        }
        */

    }

}
