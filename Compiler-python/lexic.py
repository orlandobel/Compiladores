import read
import cE
import re

class Lexic:
    def __init__(self):
        self.archivo = read.Read()
        self.patron = re.compile('[\w]+')

    def Lectura(self):
        self.aux = self.archivo.exprecion()
        while self.aux == "" or  self.aux == " " or self.aux=='\n':
            return self.Lectura()
        if not self.aux.isalpha() and not self.floatV(self.aux):
            if self.aux  in cE.carEsp:
                 return self.aux
            elif self.aux in cE.Operacion:
                return self.aux
            else:
                return "Caracter "+self.aux +" no valido"
        return self.aux

    def floatV(self, varN):
        try:
            float(varN)
            return True
        except Exception as e:
            return False
