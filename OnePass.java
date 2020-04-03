package ssmini;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class OnePass
{
public static void main(String[] args) throws FileNotFoundException,
IOException
{

HashMap<String, String> optab = new HashMap<>();
File foptab = new File("C:\\Users\\Nisha\\Desktop\\6th Sem\\SS\\SSMini\\OPTAB.DAT");
File finput = new File("C:\\Users\\Nisha\\Desktop\\6th Sem\\SS\\SSMini\\Source.DAT");
FileInputStream fipoptab = new FileInputStream(foptab);
FileInputStream fipinput = new FileInputStream(finput);
int size;
int baseRegister, PC;
String controlSection = "";
int multiOperandFlag = 0;
String[] tempoperand;
ArrayList<String> extdef = new ArrayList<>();
ArrayList<String> extref = new ArrayList<>();
String temp1 = "", temp2 = "";
int flag1 = 0;
char temp;
size = fipoptab.available();
for (int i = 0; i < size; ++i)
{
temp = (char) fipoptab.read();
if (flag1 == 0 && temp != ' ')
temp1 += temp;

else if (temp != '\n' && temp != ' ')
temp2 += temp;

if (temp == ' ')
++flag1;

if (temp == '\n')
{
optab.put(temp1, temp2);
temp1 = "";
temp2 = "";
flag1 = 0;
}
}

// Taking input from file and breaking into label, mnemonic and __ :
ArrayList<ArrayList<String>> input = new ArrayList<>();

String strtemp = "";
size = fipinput.available();
for (int j = 0; j < size; ++j)
{
temp = (char) fipinput.read();
if (temp != '\n')
{
strtemp += temp;
}
else
{
List<String> split = Arrays.asList(strtemp.split(" "));
strtemp = "";
ArrayList<String> _split = new ArrayList<>();
for (String i : split)
{
_split.add(i);
}
_split.add(0, "0");
input.add(_split);
}
}
String LOCCTR = "0";
if (!(input.get(0).get(3).isEmpty()))
{
LOCCTR = input.get(0).get(3);
}

// ASSEMBLER DIRECTIVES :

ArrayList<String> assdir = new ArrayList<>();
assdir.add("START");
assdir.add("END");
assdir.add("EXTDEF");
assdir.add("EXTREF");
assdir.add("EQU");
assdir.add("ORG");

HashMap<String, ArrayList<String>> estab = new HashMap<>();
ArrayList<String> tempestab = new ArrayList<>();
ArrayList<String> tempArrayList = new ArrayList<>();

ArrayList<ArrayList<String>> tempop = new ArrayList<>();

// SYMTAB OPERATIONS :
HashMap<String, LinkedList<String>> symtab = new HashMap<>();

for (ArrayList<String> i : input)
{
i.set(0, LOCCTR);
String label = i.get(1);
String mnemonic = i.get(2);
String operand = " ";
if (!mnemonic.equals("EXTDEF") && !mnemonic.equals("EXTREF"))
{
if (!mnemonic.equals("RSUB"))
operand = i.get(3);
}
else
{
i.add(null);
i.add(null);
}
String opcode, opaddress;
if (!(label.equals(".")))
{

//Label field handling

if (!label.equals(""))
{
if (symtab.containsKey(label))
{
LinkedList<String> SymbolList = symtab.get(label);
List<String> sublist;
sublist = SymbolList.subList(1, SymbolList.size());
if (sublist.isEmpty())
{
sublist.add(SymbolList.get(1));
}

if (SymbolList.getFirst() == null)
{
SymbolList.set(0, LOCCTR);
for (String j : sublist)
{
if (j != null)
{
ListIterator<ArrayList<String>> _tuple_ = tempop
.listIterator();
while (_tuple_.hasNext())
{
String tupleval = _tuple_.next().get(0);
if (tupleval.equals(j))
{
ArrayList<String> temp_tuple = _tuple_
.previous();
temp_tuple.set(4, temp_tuple.get(4)
+ LOCCTR);
_tuple_.set(temp_tuple);
_tuple_.next();
}
}
}
}
String string = SymbolList.getFirst();
SymbolList.clear();
SymbolList.add(string);
symtab.remove(label);
symtab.put(label, SymbolList);
}

}

else
{
LinkedList<String> tempValue = new LinkedList<>();
tempValue.add(LOCCTR);
symtab.put(label, tempValue);
}
}
}
}}}

//ASSEMBLER DIRECTIVES, MNEMONIC AND OPERAND FIELD HANDLING AND SYMTAB OPERATIONS TO BE IMPLEMENTED IN THE NEXT CODE SEGMENT