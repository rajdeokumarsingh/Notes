#!/usr/bin/perl

open(FILE, "<$ARGV[0]") or die;
$bk=$ARGV[0].".bak";
#print $bk."\n";

`rm -rf $bk`;
open(BK, ">>$bk");

$done=0;
while($line = <FILE>) {
    if($line =~ /Copyright \(C\)/ && $done==0) {
        if($line =~ /^ *\* *Copyright \(C\)/) {
            print "get c style\n";
            print BK " * Copyright (C) 2011 PEKALL Software\n";
            $done=1;
        } elsif($line =~ /^ *# *Copyright \(C\)/) {
            print "get shell style\n";
            print BK "# Copyright (C) 2011 PEKALL Software\n";
            $done=1;
        } elsif($line =~ /^ *<!-- *Copyright \(C\)/) {
            print "get xml style\n";
            print BK "<!-- Copyright (C) 2011 PEKALL Software -->\n";
            $done=1;
        }
    } 
    print BK $line;
}

close(FILE);
close(BK);

`cp $bk $ARGV[0];rm -rf $bk`
