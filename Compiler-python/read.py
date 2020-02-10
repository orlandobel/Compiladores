class Read:
    def __init__(self):
        self.file = open("test.txt","r")
## Operaciones booleanas para crear cualquier cadena
        self.numbers = lambda x : (ord(x) > 46 and ord(x) < 58);
        self.upperKy = lambda x : (ord(x) > 64 and ord(x) < 91);
        self.lowerKy = lambda x : (ord(x) > 96 and ord(x) < 123);
        self._barKey = lambda x : (ord(x) == 95);
## Combinaciones necesitadas
        self.firstLetter = lambda x : (self.upperKy(x) or self.lowerKy(x) or self._barKey(x));
        self.anyLetter =  lambda x : (self.firstLetter(x) or self.numbers(x));


    def readSpecialWord(self, nWord):
        if self.file.read(len(nWord)) == nWord:
            self.readChar()
            return nWord
        return "Syntaxis Error"

    def readChar(self):
        return self.file.read(1)

    def exprecion(self):
        cadena = ""
        x = self.readChar()

        if(self.firstLetter(x)):
            cadena += x
            x = self.readChar()

            while(self.anyLetter(x)):
                cadena += x
                x = self.readChar()

        return cadena

    def expNum(self):
        cadena=""
        x = self.readChar()
        if (x == '-'):
            cadena += x
            x = self.readChar()
        if (self.numbers(x)):
            while(self.numbers(x)):
                cadena += x
                x = self.readChar()
            if(x == '.'):
                cadena += x
                x = self.readChar()
                if (self.numbers(x)):
                    while(self.numbers(x)):
                        cadena += x
                        x = self.readChar()
                else:
                    cadena = ""
        return cadena;
