from tkinter import *

class Window():
    # declare the window
    window = Tk()
    # set window title
    window.title("Python GUI App")
    # set window width and height
    window.configure(width=900, height=700)
    # set window background color
    window.configure(bg='lightgray')
    window.mainloop()