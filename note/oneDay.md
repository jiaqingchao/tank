Frame f = new Frame();
        f.setVisible(true);
        f.setSize(800,600);
        f.setTitle("tank war");
        f.setResizable(false);

        f.addWindowListener(new WindowAdapter() {
           @Override
           public void windowClosing(WindowEvent e){
               System.exit(0);
           }
        });
初步认知Frame 类      
Frame 类
    setVisible() 设置是否可见;
    setSize() 设置是大小;
    setTitle() 设置是标题;
    setResizable() 设置是否可改变大小;
    addWindowListener() 添加window的监听器
        WindowAdapter 
            WindowListener实现类
            windowClosing方法监听window关闭按钮,window正在关
     System.exit(0) 退出窗口
     addKeyListener() 添加键盘的监听器
        KeyListener 实现类
        keyPressed(KeyEvent e) 监听键盘按下事件
        keyReleased(KeyEvent e) 监听键盘松开事件
            e.getKeyCode() 获得键盘代码
            KeyEvent.VK_XXXX 键盘按键对应的代码
    paint() Frame参数改变并刷新后调用
    repaint() 重新调用一次paint()方法
    Graphics java窗口绘图对象
        fillRect()画一个矩形
    
    
   
