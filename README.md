# JavaPing
Your assignment is to write a program called JavaPing that mimics the behavior of ping. Because there is no way
within Java to send and receive ICMP packets, you will instead implement ping the old fashioned way: by relying
on the recipient to run the Echo Protocol (RFC 862 – a pleasantly short RFC). Echo echoes all the bytes that it
receives directly back to the sender.

The Echo protocol has been superseded by ICMP and it is no longer in widespread use. It is turned off by default on
most (all?) computers. However, I was able to locate a machine here on Cedarville’s network that is for a limited
time only running the service. You can find it at IP address: 163.11.238.205. This particular server’s Echo
service is protected by a firewall and only accepts UDP packets from IP addresses in the range 163.11.0.0/16
and from port 7777.

The only things that you need to know about the ping program can be deduced from examining its output (see
screenshots above). The output from your implementation of ping should look identical to the actual ping, with one
exception: you will not be able to report the TTL since that is a network layer field (it is in the IP packet header) and
you have no access to that layer from within Java.

Note: you need to run JavaPing from a command prompt (just like the real ping) in order to receive full credit.
