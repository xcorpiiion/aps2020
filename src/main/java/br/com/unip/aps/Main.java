package br.com.unip.aps;

import br.com.unip.aps.controller.NoticiaController;
import br.com.unip.aps.service.utils.ordenacao.enums.EnumTipoOrdenacao;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static br.com.unip.aps.service.utils.ordenacao.enums.EnumTipoOrdenacao.*;
import static java.awt.Font.BOLD;
import static java.util.Collections.sort;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.SwingConstants.CENTER;
import static javax.swing.border.EtchedBorder.LOWERED;

@SpringBootApplication
@EnableAutoConfiguration
public class Main extends JFrame implements CommandLineRunner {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;

    private JTextField txtProcurarNotificia;

    private List<JPanel> panelLinhas;

    private JPanel panelBody;

    private JPanel panelHeader;

    private JButton btnPesquisar;

    private JButton btnMudarPagina;

    private JButton btnFiltro;

    private JComboBox<Integer> comboBoxMudarPagina;

    private JComboBox<Integer> comboBoxDateInicio;

    private JComboBox<Integer> comboBoxDateFim;

    private JCheckBox isCheckBoxOrderByTitulo;

    private JCheckBox checkBoxOrderByTituloIsAsc;

    private JCheckBox checkBoxOrderByDateIsAsc;

    private JCheckBox isCheckBoxOrderByDate;

    @Autowired
    private NoticiaController noticiaController;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        EventQueue.invokeLater(() -> {
            try {
                init();
                setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100, 100, 763, 420);
        criaPanelCentral();
        noticiaController.init();
        criaHeader();
        iniciaCheckBox();
        criaBody();
        criaComboBoxPages(contentPane);
        criaButtonMudarPages(contentPane);
        criaComboBoxData(panelHeader);
        criaBodyNoticia(this.comboBoxMudarPagina.getSelectedIndex() + 1);
        handlePesquisar();
        handleMudarPage();
        handleFiltro();
    }

    private void iniciaCheckBox() {
        this.checkBoxOrderByTituloIsAsc = criaCheckBox("Titulo por ordem crescente", 131, 50);
        this.isCheckBoxOrderByTitulo = criaCheckBox("Ordenar por titulo", 131, 70);

        this.isCheckBoxOrderByDate = criaCheckBox("Ordenar por data", 350, 70);
        this.checkBoxOrderByDateIsAsc = criaCheckBox("Data por ordem crescente", 350, 50);

        this.isCheckBoxOrderByDate.setSelected(false);
        this.checkBoxOrderByDateIsAsc.setSelected(false);
        this.isCheckBoxOrderByTitulo.setSelected(true);
        this.checkBoxOrderByTituloIsAsc.setSelected(true);
        criaEventosCheckBox();
    }

    private void criaEventosCheckBox() {
        this.isCheckBoxOrderByTitulo.addActionListener(e -> {
            isCheckBoxOrderByDate.setSelected(false);
            checkBoxOrderByDateIsAsc.setSelected(false);
        });
        this.checkBoxOrderByTituloIsAsc.addActionListener(e -> {
            isCheckBoxOrderByDate.setSelected(false);
            checkBoxOrderByDateIsAsc.setSelected(false);
            isCheckBoxOrderByTitulo.setSelected(true);
        });
        this.isCheckBoxOrderByDate.addActionListener(e -> {
            isCheckBoxOrderByTitulo.setSelected(false);
            checkBoxOrderByTituloIsAsc.setSelected(false);
        });
        this.checkBoxOrderByDateIsAsc.addActionListener(e -> {
            isCheckBoxOrderByTitulo.setSelected(false);
            checkBoxOrderByTituloIsAsc.setSelected(false);
            isCheckBoxOrderByDate.setSelected(true);
        });
    }

    private void criaPanelCentral() {
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
    }

    private void criaButtonMudarPages(JPanel contentPane) {
        this.btnMudarPagina = new JButton("next");
        this.btnMudarPagina.setBounds(650, 350, 80, 20);
        contentPane.add(this.btnMudarPagina);
    }

    public void handleMudarPage() {
        this.btnMudarPagina.addActionListener(e -> mudaPage());
    }

    private void handleFiltro() {
        this.btnFiltro.addActionListener(e -> {
            if (defineOrdenacao() == TITULO) {
                noticiaController.findByDate((Integer) comboBoxDateInicio.getSelectedItem(), (Integer) comboBoxDateFim.getSelectedItem(), TITULO, checkBoxOrderByTituloIsAsc.isSelected());
            } else if (defineOrdenacao() == DATA) {
                noticiaController.findByDate((Integer) comboBoxDateInicio.getSelectedItem(), (Integer) comboBoxDateFim.getSelectedItem(), DATA, checkBoxOrderByDateIsAsc.isSelected());
            } else {
                noticiaController.findByDate((Integer) comboBoxDateInicio.getSelectedItem(), (Integer) comboBoxDateFim.getSelectedItem(), ID, true);
            }
            for (Integer numberPage : noticiaController.getNumberPages()) {
                comboBoxMudarPagina.removeItem(numberPage);
            }
            noticiaController.defineNumeroPagina();
            for (Integer numberPage : noticiaController.getNumberPages()) {
                comboBoxMudarPagina.addItem(numberPage);
            }
            mudaPage();
        });
    }

    private void handlePesquisar() {
        this.btnPesquisar.addActionListener(e -> {
            if (defineOrdenacao() == TITULO) {
                noticiaController.findByTitulo(txtProcurarNotificia.getText(), TITULO, checkBoxOrderByTituloIsAsc.isSelected());
            } else if (defineOrdenacao() == DATA) {
                noticiaController.findByTitulo(txtProcurarNotificia.getText(), DATA, checkBoxOrderByDateIsAsc.isSelected());
            } else {
                noticiaController.findByTitulo(txtProcurarNotificia.getText(), ID, true);
            }
            for (Integer numberPage : noticiaController.getNumberPages()) {
                comboBoxMudarPagina.removeItem(numberPage);
            }
            noticiaController.defineNumeroPagina();
            for (Integer numberPage : noticiaController.getNumberPages()) {
                comboBoxMudarPagina.addItem(numberPage);
            }
            mudaPage();
        });
    }

    private EnumTipoOrdenacao defineOrdenacao() {
        if (isCheckBoxOrderByTitulo.isSelected() && (checkBoxOrderByTituloIsAsc.isSelected() ||
                !checkBoxOrderByTituloIsAsc.isSelected())) {
            return TITULO;
        } else if (isCheckBoxOrderByDate.isSelected() && (checkBoxOrderByDateIsAsc.isSelected() ||
                !checkBoxOrderByDateIsAsc.isSelected())) {
            return DATA;
        } else if (!isCheckBoxOrderByTitulo.isSelected() && !checkBoxOrderByTituloIsAsc.isSelected() &&
                (!isCheckBoxOrderByDate.isSelected() && !checkBoxOrderByDateIsAsc.isSelected())) {
            return ID;
        }
        final String exceptionMensagem = "Desmarque os check boxs para ordernar por ID, ou selecione os check boxs" +
                "de titulo ou de ordenação para ser ordenado por eles";
        JOptionPane.showMessageDialog(panelBody, exceptionMensagem, "Error ao ordenar dados", ERROR_MESSAGE);
        throw new IllegalStateException(exceptionMensagem);
    }

    private void mudaPage() {
        for (JPanel linha : this.panelLinhas) {
            linha.removeAll();
            linha.setVisible(false);
        }
        this.panelLinhas.clear();
        criaBodyNoticia(this.comboBoxMudarPagina.getSelectedIndex() + 1);
    }

    private JCheckBox criaCheckBox(String checkBoxName, int x, int y) {
        JCheckBox yourCheckBox = new JCheckBox(checkBoxName);
        yourCheckBox.setBounds(x, y, 180, 20);
        this.panelHeader.add(yourCheckBox);
        return yourCheckBox;
    }

    private void criaComboBoxPages(JPanel contentPane) {
        this.comboBoxMudarPagina = new JComboBox<>();
        for (Integer numberPage : this.noticiaController.getNumberPages()) {
            this.comboBoxMudarPagina.addItem(numberPage);
        }
        this.comboBoxMudarPagina.setBounds(600, 350, 50, 20);
        contentPane.add(this.comboBoxMudarPagina);
    }

    private void criaComboBoxData(JPanel panelHeader) {
        this.comboBoxDateInicio = new JComboBox<>();
        this.comboBoxDateFim = new JComboBox<>();
        List<Integer> anos = new ArrayList<>();
        for (DateTime dataNoticia : this.noticiaController.getDataNoticias()) {
            anos.add(dataNoticia.getYear());
        }
        sort(anos);
        for (Integer ano : anos) {
            this.comboBoxDateInicio.addItem(ano);
            this.comboBoxDateFim.addItem(ano);
        }
        JLabel lblDe = new JLabel("De:");
        criaLabel(panelHeader, lblDe, 10, 5, 60, 20, 18);
        this.comboBoxDateInicio.setBounds(60, 5, 60, 20);
        JLabel lblAte = new JLabel("Até:");
        criaLabel(panelHeader, lblAte, 150, 5, 60, 20, 18);
        this.comboBoxDateFim.setBounds(200, 5, 60, 20);
        panelHeader.add(this.comboBoxDateInicio);
        panelHeader.add(this.comboBoxDateFim);
    }

    private void criaBody() {
        panelBody = new JPanel();
        criaPanel(panelBody, 0, 96, 737, 244, contentPane);

        JPanel panelBodyTitle = new JPanel();
        criaPanel(panelBodyTitle, 10, 5, 717, 28, panelBody);

        JLabel lblTitulo = new JLabel("Título");
        criaLabel(panelBodyTitle, lblTitulo, 0, 0, 102, 28, 18);

        JLabel lblData = new JLabel("Data da notítica");
        criaLabel(panelBodyTitle, lblData, 350, 0, 153, 28, 18);

        JLabel lblVisualizar = new JLabel("Visualizar");
        criaLabel(panelBodyTitle, lblVisualizar, 554, 0, 153, 28, 18);

        JPanel panelLinha = new JPanel();
        criaPanel(panelLinha, 10, 0, 717, 28, panelBody);

    }

    private void criaBodyNoticia(int numberPage) {
        panelLinhas = new ArrayList<>();
        noticiaController.criaNoticia(panelLinhas, panelBody, numberPage);
    }

    private void criaHeader() {
        panelHeader = new JPanel();
        criaPanel(panelHeader, 0, 0, 737, 93, contentPane);

        txtProcurarNotificia = new JTextField();
        txtProcurarNotificia.setBounds(131, 30, 447, 20);
        panelHeader.add(txtProcurarNotificia);
        txtProcurarNotificia.setColumns(10);

        String pesquisar_notícia = "Pesquisar notícia";
        JLabel lblPesquisa = new JLabel(pesquisar_notícia);
        criaLabel(panelHeader, lblPesquisa, 0, 30, 135, 20, 14);

        btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setBounds(580, 30, 135, 20);
        panelHeader.add(btnPesquisar);
        btnFiltro = new JButton("Filtrar");
        btnFiltro.setBounds(270, 5, 70, 20);
        panelHeader.add(btnFiltro);
    }

    private void criaLabel(JPanel panel, JLabel lblVisualizar, int x, int y, int width, int height, int fontSize) {
        lblVisualizar.setHorizontalAlignment(CENTER);
        lblVisualizar.setFont(new Font("Arial", BOLD, fontSize));
        lblVisualizar.setBounds(x, y, width, height);
        panel.add(lblVisualizar);
    }

    private void criaPanel(JPanel panelLinha, int x, int y, int width, int height, JPanel panelBody) {
        panelLinha.setBorder(new EtchedBorder(LOWERED, null, null));
        panelLinha.setBounds(x, y, width, height);
        panelBody.add(panelLinha);
        panelLinha.setLayout(null);
    }
}
