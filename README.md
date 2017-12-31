# Mesh

## Quick start

* Use Samsung Galaxy S4
* Flash LineageOS 14.1
* Install su (addonsu-14.1-arm-signed.zip)
* Disable WiFi in Android settings
* Install this app
* Choose an IP address and network name. Note: The IP address must be *unique* on the network, and the network name should be the same for all phones.
* Click "Start Mesh network" to connect to the network
* Click "Run OLSRD" to build the mesh

## Details

This is a small app to build a Mesh network using [OLSR](https://en.wikipedia.org/wiki/Optimized_Link_State_Routing_Protocol).
It is very similar to [MANET Manager](https://play.google.com/store/apps/details?id=org.span).

**This app was built to run exclusively on the Samsung Galaxy S4 (i9505)**

The app requires WiFi Ad-Hoc mode, which is generally not support by Android (see [here](https://issuetracker.google.com/issues/36904180)).  
In order to enable it, the app requires **root access** to load a different network driver, and it expects to find these files on the phone:

* /system/etc/wifi/bcmdhd_ibss.bin
* /system/etc/wifi/nvram_net.txt

They are available in LineageOS 14.1 for jftlexx.

So if you own a S4, flash LineageOS, install su and you should be ready to run this app.

If you own a similar phone (I've tested the i9506 and the Samsung Note 3 (SM-N9005) successfully) or a phone with the same bcmdhd driver it might also work.

## WiFi Ad-Hoc

First step is to enable WiFi Ad-Hoc mode on your phone.

Please note that it was necessary to set a fixed BSSID in order to connect properly to the network.
This is hard-coded and set to 02:11:22:33:44:55.  
If this wouldn't be hard-coded, you might end up with two networks with the name SSID, but different BSSIDs which aren't able to communicate between each other.

Second note: Subnet mask is also hard-coded to 255.0.0.0

## OLSR

The app uses OLSR as the routing protocol.
It comes with a precompiled binary for olsrd and the jsoninfo plugin.

After you've set up the Ad-Hoc network, press "Start OLSR" to get the daemon running in the background.
It will begin to automatically detect other OLSR clients in the network.

All detected neighbors are shown in the app.

If olsrd is running and you connect to the device via adb, you can access the jsoninfo data by:

```
curl http://localhost:9090/all
```

### How to build from source

Quote from Andrew Hunter <andrew.hunter@utexas.edu>: 

> For olsrd (git clone https://github.com/OLSR/olsrd.git), I had to change a couple of things:
>
> 1. in make/Makefile.android add -pie to LDFLAGS (line 124 I think)
> 2. in make/Makefile.android remove the option -mthumb-interwork from the CPPFLAGS (around line 100)
> 3. rewrite the end of make/Makefile.android.compiler like this:
> 
> ```
> NDK_ABIDIR = linux-androideabi
> NDK_SYSROOT = $(NDK_BASE)/sysroot
> NDK_UNAME = $(shell uname -s | tr '[A-Z]' '[a-z]')
> NDK_TOOLCHAIN = $(NDK_BASE)/
> CROSS_COMPILE = $(NDK_BASE)/bin/
>  
> CC = $(CROSS_COMPILE)clang --sysroot="$(NDK_SYSROOT)"
> LD = $(CROSS_COMPILE)arm-linux-androideabi-ld
> AR = $(CROSS_COMPILE)llvm-ar
> ```
> 
> Then I compiled it with:
> 
> ```
> $ make OS=android NDK_BASE=/path/to/my/ndk/standalone/toolchain/ build_all
> ```
> 
> And then I ran it as root on the devices from /data/local/tmp:
> 
> ```
> $ ./olsrd -i wlan0 > olsr.log &
> ```
> 
> This worked and began installing routes once multiple devices could see each other.

## Driver details

Andrew Hunter <andrew.hunter@utexas.edu> found a way to enable the IBSS driver on the S4.
He has insane knowledge about mesh routing and showed me step-by-step how to set up Ad-Hoc mode and OLSR on Android.

Blog posts:

* [Mesh networks on Android?](http://andreas-mausch.de/blog/2017/07/20/mesh-networks-on-android.html)
* [Samsung S4: Ad-Hoc WiFi (IBSS) on LineageOS 14.1](http://andreas-mausch.de/blog/2017/12/11/samsung-s4-adhoc-on-lineage14.1.html)

### bcmdhd vs brcmfmac

> bcmdhd is what broadcom open-sourced for android. Separately, we started 
  working on brcmfmac for the mainline linux kernel. Both drivers come 
  from the same proprietary driver. The goal is to have brcmfmac work on 
  Android as well and we have most features on board for that.

Source: [gmane.org](http://thread.gmane.org/gmane.linux.kernel.wireless.general/107344)

## Known issues

Not tested at all.  
No power management, I got no idea what happens after the phone goes into standby mode.

Sometimes the olsrd daemon hangs and you need to force kill it. I should add the watchdog plugin to avoid this.
