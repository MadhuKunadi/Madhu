package com.cs;

import static java.util.concurrent.TimeUnit.*;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ServiceControl implements ServletContextListener{
	
	private final static ScheduledExecutorService scheduler = 
			Executors.newScheduledThreadPool(1);

		public static void beepForAnHour() {
		    final Runnable beeper = new Runnable() {
		            public void run() { 
		            	System.out.println("*----THREAD STARTED----*");
		            	Transactions.addTransactions();
//		            	DoctorCalender.updatePendingAppointments();
		            	
		            	System.out.println("*----THREAD COMPLTED----* ");
		            }
		        };
		    final ScheduledFuture<?> beeperHandle = 
		        scheduler.scheduleAtFixedRate(beeper, delay(), 24*60*60, SECONDS);
		
//beepForAnHour();
	}
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
 //Notification that the servlet context is about to be shut down.
//		beepForAnHour();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
// do all the tasks that you need to perform just after the server starts
		beepForAnHour();	
//Notification that the web application initialization process is starting
	}
	
	public static  long delay() {
		long delay=0;
		Calendar c = Calendar.getInstance();
		    c.add(Calendar.DAY_OF_MONTH, 1);
	        c.set(Calendar.HOUR_OF_DAY, 0);
	        c.set(Calendar.MINUTE, 0);
	        c.set(Calendar.SECOND, 0);
	        c.set(Calendar.MILLISECOND, 0);
        delay = (c.getTimeInMillis()/1000-System.currentTimeMillis()/1000);
        //System.out.println("DELAY TO START THE SERVICE  : "+delay+" SECONDS");
        
//        try {
//			System.out.println("DELAY TO START THE SERVICE  : "+getLocalHostLANAddress());
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
        InetAddress IP = null;
		try {
			IP = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //System.out.println("IP OF THE SYSTEM IS  := "+IP.getHostAddress());
        InetAddress addr = null;
		try {
			addr = InetAddress.getByName(IP.getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String host = addr.getHostName();
        //System.out.println("SYSTEM NAME IS  := "+host);
//        DoctorCalender.customerService("*-----SERVICE HAS BEEN STARTED-------*","*-----\"DELAY TO START THE SERVICE  : "+delay+" SECONDS\"-------*  SERVICE HAS STARTED IN THE SYSTEM IS := "+host+" AND IP ADRESS  := "+IP.getHostAddress()+" ");
		return delay;	
	
}
	
	
}
