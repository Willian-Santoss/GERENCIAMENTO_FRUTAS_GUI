package GERENCIAMENTO;

import javax.swing.*; //biblioteca para criar a interface gráfica.
import java.awt.*; // biblioteca para layout e interface.
import java.awt.event.ActionEvent; // biblioteca para interação com a interface.
import java.awt.event.ActionListener; // biblioteca para armazenamento das informações.
import java.util.ArrayList;

public class FruitManagerGUI { // classe principal do projeto
    private ArrayList<String> frutas; // toda vez que uma fruta é adicionada, modifica ou removida na interface. Esse Arraylist é atualizado
    private DefaultListModel<String> listModel; // atualiza as informações na tela
    private JList<String> list; // cria componentes para exibir na interface

    public FruitManagerGUI() {
    	// inicializando frutas, listaModel e list
        frutas = new ArrayList<>(); 
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);

        JFrame frame = new JFrame("Gerenciador de Frutas"); // cria a janela principal da tela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // método para fechar a janela
        frame.setSize(400, 300); // altura e largura da janela
        frame.setLayout(new BorderLayout()); // tipo de layout da tela principal

        JPanel panel = new JPanel();  // cria um painel
        panel.setLayout(new GridLayout(2, 1)); // esse painel tem o layout GridLayout de 2 linhas e 1 coluna

        JTextField frutaField = new JTextField(); // cria o campo para a entrada do nome da fruta
        panel.add(frutaField); // e adiciona  nome da fruta ao panel

        JButton addButton = new JButton("Adicionar"); // cria o botão Adicionar
        panel.add(addButton); // adiciona o botão ao painel

        JButton modifyButton = new JButton("Modificar"); // cria o botão modificar
        modifyButton.setEnabled(false); // inicia desabilitado e só é habilitado se uma fruta for adicionada
        panel.add(modifyButton); // adiciona o botão modificar ao painel

        JButton removeButton = new JButton("Remover"); // cria o botão remover
        removeButton.setEnabled(false); // inicia desabilitado e só é habilitado se uma fruta for adicionada
        panel.add(removeButton); // adiciona o botão remover ao painel

        frame.add(panel, BorderLayout.NORTH); // posição dos botões na parte de cima da tela principal
        
        list = new JList<>(listModel); // especifica o local onde o scroll vai ficar
        JScrollPane scrollPane = new JScrollPane(list); // cria um scroll para descer e subir a tela da lista
        frame.add(scrollPane, BorderLayout.CENTER); // posição do scroll na tela principal

        JButton listButton = new JButton("Listar Frutas"); // cria o botão Listar Frutas
        frame.add(listButton, BorderLayout.SOUTH); // define a posição dele na tela principal

        addButton.addActionListener(new ActionListener() { // especifica que esta definindo a função do botão adicionar
            @Override
            public void actionPerformed(ActionEvent e) { // quando o botão adicionar for pressionado ele executa o método descrito nas proximas linhas
                String novaFruta = frutaField.getText(); // define novaFruta com o texto do campo frutaField
                if (!novaFruta.isEmpty()) { // se novaFruta for diferente de vazio executa o proximo método
                    frutas.add(novaFruta); // adiciona fruta a lista lógica
                    listModel.addElement(novaFruta); //adiciona nova fruta a lista visual
                    frutaField.setText(""); // deixa o campo frutaField em branco
                    JOptionPane.showMessageDialog(frame, novaFruta + " foi adicionada."); // estrutura da imagem que é exibida quando adicionar uma fruta
                }
            }
        });

        modifyButton.addActionListener(new ActionListener() { // especifica que esta definindo a função do botão modificar
            @Override
            public void actionPerformed(ActionEvent e) { // quando o botão modificar for pressionado ele executa o método descrito nas proximas linhas
                int selectedIndex = list.getSelectedIndex(); // método para selecionar qual fruta quer modificar
                if (selectedIndex != -1) { // o método para selecionar precisa ser um número positivo
                    String frutaSelecionada = listModel.getElementAt(selectedIndex); // armazena na frutaSelecionada o valor da Jlist selecionada pelo Index
                    String novaFruta = JOptionPane.showInputDialog(frame, "Modificar " + frutaSelecionada + " para:", frutaSelecionada); // estrutura para apresentar na tela quando for modificar
                    if (novaFruta != null && !novaFruta.isEmpty()) { // se novaFruta for diferente de vazio executa o proximo método
                        frutas.set(selectedIndex, novaFruta); // salva a modificação na lista logica
                        listModel.set(selectedIndex, novaFruta); // adiciona a fruta modificada a lista visual
                        JOptionPane.showMessageDialog(frame, "Fruta " + frutaSelecionada + " foi modificada para " + novaFruta); // // estrutura para apresentar na tela após a modificação
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Selecione uma fruta para modificar."); // se o if anterior for false ele deve selecionar uma fruta para modificar
                }
            }
        });

        removeButton.addActionListener(new ActionListener() { // especifica que esta definindo a função do botão remover
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtem o índice do item selecionado na lista
                int selectedIndex = list.getSelectedIndex();
                if (selectedIndex != -1) {
                    String frutaRemovida = frutas.remove(selectedIndex);
                    listModel.remove(selectedIndex); // remove o item na lista logica
                    JOptionPane.showMessageDialog(frame, "Fruta removida: " + frutaRemovida); // remove a fruta da lista visual
                } else {
                    JOptionPane.showMessageDialog(frame, "Selecione uma fruta para remover."); // se o if anterior for false ele deve selecionar uma fruta para remover
                }
            }
        });

        listButton.addActionListener(new ActionListener() { // especifica que esta definindo a função do botão Listar Frutas
            @Override
            public void actionPerformed(ActionEvent e) { 
                if (frutas.isEmpty()) { // se frutas estiver vazio
                    JOptionPane.showMessageDialog(frame, "Nenhuma fruta na lista."); // mensagem caso frutas esteja vazio
                } else {
                    JOptionPane.showMessageDialog(frame, "Frutas: " + frutas);  // se o if anterior for false ele apresenta as frutas salvas
                }
            }
        });

        // Adicionando um ouvinte para seleção da lista
        list.addListSelectionListener(e -> {
            boolean selectionExists = !list.isSelectionEmpty();
            removeButton.setEnabled(selectionExists);
            modifyButton.setEnabled(selectionExists);
        });

        // Tornar a janela visível
        frame.setVisible(true);
    }
        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() { // método que agenda uma tarefa para ser executada na EDT
                @Override
                public void run() { // método que contém o código a ser executado na EDT
                    new FruitManagerGUI(); // cria uma nova instância da interface gráfica principal
                }
            });
        }
}
