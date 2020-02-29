from cE import carEsp as c
from cE import Operacion as op
class Read:
    def __init__(self):
        self.file = open("test.txt","r")
        self.x = ''
## Operaciones booleanas para crear cualquier cadena
        self.numbers = lambda x : (ord(x) > 46 and ord(x) < 58);
        self.upperKy = lambda x : (ord(x) > 64 and ord(x) < 91);
        self.lowerKy = lambda x : (ord(x) > 96 and ord(x) < 123);
        self._barKey = lambda x : (ord(x) == 95);
## Combinaciones necesitadas
        self.firstLetter = lambda x : (self.upperKy(x) or self.lowerKy(x) or self._barKey(x));
        self.anyLetter =  lambda x : (self.firstLetter(x) or self.numbers(x));


    def EspCarcter(self):
        if (self.x == "" or self.x== " " or self.x== "\n"):
            aux =self.x
            self.x = self.readChar()
            return aux

        valRead = self.x
        if valRead in op:
            self.x = self.readChar()
            aux = valRead+self.x
            if aux in c:
                valRead = aux
                self.x = self.readChar()
        else:
            self.x = self.readChar()
            aux = valRead+self.x
            if aux in c:
                valRead = aux
                self.x = self.readChar()
        return valRead

    def readChar(self):
        return self.file.read(1)

    def exprecion(self):
        cadena = ""

        if (self.x == "" or self.x == " "):
            self.x = self.readChar()

        if(self.firstLetter(self.x)):
            cadena += self.x
            self.x = self.readChar()
            while(self.anyLetter(self.x)):
                cadena += self.x
                self.x = self.readChar()
        elif(self.numbers(self.x)):
            cadena += self.expNum()
        else:
            cadena += self.EspCarcter()

        return cadena

    def expNum(self):
        cadena=""
        if (self.numbers(self.x)):
            while(self.numbers(self.x)):
                cadena += self.x
                self.x = self.readChar()
            if(self.x == '.'):
                cadena += self.x
                self.x = self.readChar()
                if (self.numbers(self.x)):
                    while(self.numbers(self.x)):
                        cadena += self.x
                        self.x = self.readChar()
                else:
                    cadena = ""
        return cadena;

#    def ifStatement(self):
#        if (self.exprecion()=="if"):
#            self.x = self.readChar()
#            if (self.x == '('):
#                self.exprecion()
#                self.EspCarcter()
#                self.exprecion()
#                self.x = self.readChar()
#                if(self.x == ')'):
