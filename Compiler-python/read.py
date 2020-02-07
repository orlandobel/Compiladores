class Read:
    def __init__(self):
        self.file = open("test.txt","r")

    def readChar(self):
        return self.file.read(1)

    def exprecion(self):
        cadena = ""
        x = self.readChar()

        if((ord(x) > 64 and ord(x) < 91) or (ord(x) > 96 and ord(x) < 123) or ord(x) == 95):
            cadena += x
            x = self.readChar()

            while((ord(x) > 64 and ord(x) < 91) or (ord(x) > 96 and ord(x) < 123) or (ord(x) > 46 and ord(x) < 58) or ord(x) == 95):
                cadena += x
                x = self.readChar()

        return cadena

    def expNum(self):
        cadena=""
        x = self.readChar()
        if (ord(x) > 46 and ord(x) < 58):
            while(ord(x) > 46 and ord(x) < 58):
                cadena += x
                x = self.readChar()
            if(x == '.'):
                cadena += x
                x = self.readChar()
                if (ord(x) > 46 and ord(x) < 58):
                    while(ord(x) > 46 and ord(x) < 58):
                        cadena += x
                        x = self.readChar()
                else:
                    cadena = ""
        return cadena;
