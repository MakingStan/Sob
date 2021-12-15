/*
 * Copyright (c) 2021, MakingStan
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.stan;

import org.stan.commands.TotalMessageCommand;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class TotalMessageHandler {
    //sorry if this code is very messy its my first database "kindof" thing :)

    public static final File parent = new File("/Users/Stan/programming/programming/discord bots/Sob Bott/msges.txt");
    public static String datas;
    public static int data = 0;

    public static void handle() throws IOException
    {
        if(parent.createNewFile())
        {
            System.out.println("file created succesfully");
        }
        Scanner scanner = new Scanner(parent);

        FileReader read = new FileReader(parent);
        BufferedReader reader = new BufferedReader(read);
        if(reader.read() == -1)
        {
            data++;
            datas = String.valueOf(data);
            System.out.println(datas);
            FileWriter fw = new FileWriter(parent);
            BufferedWriter buffer = new BufferedWriter(fw);
            buffer.write(datas+" ");
            buffer.close();

            TotalMessageCommand.msgCount = data;

        }
        else
        {
            data = scanner.nextInt();
            data++;
            datas = String.valueOf(data);
            FileWriter fw = new FileWriter(parent);
            BufferedWriter buffer = new BufferedWriter(fw);
            buffer.write(datas+" ");
            buffer.close();

            TotalMessageCommand.msgCount = data;
        }
        try
        {
            System.out.println(reader.read());
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
        reader.close();
    }

    public static void start() throws IOException
    {
        if(parent.createNewFile())
        {
            System.out.println("first file created");
        }
        Scanner scanner = new Scanner(parent);

        FileReader read = new FileReader(parent);
        BufferedReader reader = new BufferedReader(read);

        if(reader.read() == -1)
        {
            datas = String.valueOf(data);
        }
        else
        {
            data = scanner.nextInt();
            datas = String.valueOf(data);


            TotalMessageCommand.msgCount = data;

        }
    }

    public static void reset() throws IOException {
        String resetConfirm = JOptionPane.showInputDialog(null, "Do you want to reset your points?\nType \"yes\" to confirm");

        if(resetConfirm != null)
        {
            if(resetConfirm.equalsIgnoreCase("yes"))
            {
                data = 0;
                datas = String.valueOf(data);

                TotalMessageCommand.msgCount = data;

                FileWriter fw = new FileWriter(parent);
                BufferedWriter buffer = new BufferedWriter(fw);

                buffer.write(datas);
                buffer.close();
            }
            else
            {
                System.out.println("the user cancelled resetting points");
            }
        }
    }
}


