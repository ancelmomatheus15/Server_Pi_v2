package client;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class TesteMAC_PI {

	public static void main(String[] args) {
		busca();
		busca2();
	}
	
	public static void busca (){
		
		try {
            InetAddress address = InetAddress.getLocalHost();
            //InetAddress address = InetAddress.getByName("192.168.46.53");

            /*
             * Get NetworkInterface for the current host and then read
             * the hardware address.
             */
            NetworkInterface ni = NetworkInterface.getByInetAddress(address);
            
            if (ni != null) {
                byte[] mac = ni.getHardwareAddress();
                if (mac != null) {
                    /*
                     * Extract each array of mac address and convert it 
                     * to hexa with the following format 
                     * 08-00-27-DC-4A-9E.
                     */
                    for (int i = 0; i < mac.length; i++) {
                        System.out.format("%02X%s",
                                mac[i], (i < mac.length - 1) ? "-" : "");
                    }
                    System.out.println("\n");
                } else {
                    System.out.println("Address doesn't exist or is not " +
                            "accessible.");
                }
            } else {
                System.out.println("Network Interface for the specified " +
                        "address is not found.");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
		
	}
	
	public static void busca2(){
		
		try {

	        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
	        while(networkInterfaces.hasMoreElements())
	        {
	            NetworkInterface network = networkInterfaces.nextElement();
	            System.out.println("network : " + network);
	            byte[] mac = network.getHardwareAddress();
	            if(mac == null)
	            {
	                System.out.println("null mac");             
	            }
	            else
	            {
	                System.out.print("MAC address : ");

	                StringBuilder sb = new StringBuilder();
	                for (int i = 0; i < mac.length; i++)
	                {
	                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));        
	                }
	                System.out.println(sb.toString());  
	                break;
	            }
	        }
	    } catch (SocketException e){

	        e.printStackTrace();

	    }
	}

}
