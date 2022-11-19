using System;
using System.Collections.Generic;
using Lab_4.Implementation;
using Lab4.Implementation;

namespace Lab4
{
    class Program
    {
        static void Main()
        {
            var hosts = new List<string> {
                "www.cs.ubbcluj.ro/~hfpop/teaching/pfl/requirements.html",
                "www.cs.ubbcluj.ro/~forest/newton/index.html",
                "www.cs.ubbcluj.ro/~rlupsa/edu/pdp/index.html"
            };

            //DirectCallBack.Run(hosts);  
            //TaskMechanism.Run(hosts);
            AsyncAwaitTasksMechanism.Run(hosts);
            Console.ReadLine();

            //Console.WriteLine(CallBack.parseURL("www.cs.ubbcluj.ro/~rlupsa"));
        }
    }
}
