package com.miempresa.aplicacion.controladores;

import com.miempresa.aplicacion.dtos.FacturaDto;
import com.miempresa.aplicacion.modelos.RepositorioFactura;
import com.miempresa.aplicacion.modelos.Factura;
import com.miempresa.aplicacion.modelos.Login;
import com.miempresa.aplicacion.modelos.Producto;
import com.miempresa.aplicacion.modelos.RepositorioLogin;
import com.miempresa.aplicacion.modelos.RepositorioProducto;
import com.miempresa.aplicacion.modelos.RepositorioVendedor;
import com.miempresa.aplicacion.modelos.Vendedor;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor(onConstructor = @__(
        @Autowired))
public class ControladorFactura {

    private final RepositorioFactura repositorioFactura;
    private final RepositorioProducto repositorioProducto;
    private final RepositorioVendedor repositorioVendedor;
    private final RepositorioLogin repositorioLogin;

    @GetMapping("/volverF")
    public RedirectView volver(Model model) {
        Iterable<Login> login = repositorioLogin.findAll();
        ArrayList<String> cod = new ArrayList();
        for (Login i : login) {
            cod.add(i.getRolUser());
        }
        Object[] codigo = cod.toArray();
        if (codigo[codigo.length - 1].equals("VENDEDOR")) {
            return new RedirectView("/facturasV", true);
        }
        return new RedirectView("/facturasA");
    }

    @GetMapping("/facturasA") //path del controlador
    public String getTodasLasFacturasA(Model model) {
        Iterable<Factura> facturas = repositorioFactura.findAll();
        Iterable<Login> login = repositorioLogin.findAll();
        ArrayList<String> cod = new ArrayList();
        ArrayList<String> nom = new ArrayList();
        for (Login i : login) {
            cod.add(i.getRolUser());
            nom.add(i.getNombreUser());
        }
        Object[] codigo = cod.toArray();
        Object[] nombre = nom.toArray();
        model.addAttribute("nombre", nombre[nombre.length - 1]);
        model.addAttribute("rol", codigo[codigo.length - 1]);
        model.addAttribute("facturas", facturas);
        model.addAttribute("factura", new FacturaDto());
        return "vistaFormularioAdminFacturas";
    }

    @GetMapping("/facturas/{numeroFactura}") //path del controlador
    public String getFacturaByNumero(@PathVariable String numeroFactura, Model model) {
        Iterable<Login> login = repositorioLogin.findAll();
        ArrayList<String> cod = new ArrayList();
        ArrayList<String> nom = new ArrayList();
        for (Login i : login) {
            cod.add(i.getRolUser());
            nom.add(i.getNombreUser());
        }
        Object[] codigo = cod.toArray();
        Object[] nombre = nom.toArray();
        model.addAttribute("nombre", nombre[nombre.length - 1]);
        model.addAttribute("rol", codigo[codigo.length - 1]);
        Factura factura = repositorioFactura.findByNumeroFactura(numeroFactura);
        model.addAttribute("facturas", factura);
        return "vistaFactura";
    }

    @GetMapping("/crear/factura") //path del controlador
    public RedirectView crearFactura(Model model) {

        model.addAttribute("factura", new FacturaDto());
        return new RedirectView("/facturasA");
    }

    @GetMapping("/consultar/factura") //path del controlador
    public RedirectView findFactura(Model model) {
        model.addAttribute("factura", new FacturaDto());
        return new RedirectView("/facturasA", true);
    }

    @PostMapping("/consultar/factura") //path del controlador
    public String procesarFactura(@ModelAttribute FacturaDto facturaDto, Model model) {

        boolean flag = false;
        if (facturaDto.getCodigoVendedor() != null && facturaDto.getCodigoVendedor() != "") {

            Vendedor vendedor = repositorioVendedor.findByCodVendedor(facturaDto.getCodigoVendedor());

            if (vendedor != null) {

                Iterable<Factura> facturas = repositorioFactura.findAll();
                List<Factura> facturas2 = new ArrayList<>();

                for (Factura l : facturas) {

                    if (l.getVendedor().getCodVendedor().equals(vendedor.getCodVendedor())) {
                        flag = true;
                        facturas2.add(l);
                    }
                }
                if (flag == false) {
                    return "redirect:/consultar/factura";
                }
                Iterable<Login> login = repositorioLogin.findAll();
                ArrayList<String> cod = new ArrayList();
                ArrayList<String> nom = new ArrayList();
                for (Login i : login) {
                    cod.add(i.getRolUser());
                    nom.add(i.getNombreUser());
                }
                Object[] codigo = cod.toArray();
                Object[] nombre = nom.toArray();
                model.addAttribute("nombre", nombre[nombre.length - 1]);
                model.addAttribute("rol", codigo[codigo.length - 1]);
                model.addAttribute("facturas", facturas2);
                return "vistaFactura";
            } else {

                return "redirect:/facturas";
            }
        } else {

            System.out.println("Numero Factura");
            Factura factura = repositorioFactura.findByNumeroFactura(facturaDto.getNumeroFactura());
            if (factura != null) {
                return "redirect:/facturas/" + factura.getNumeroFactura();
            } else {
                return "redirect:/consultar/factura";
            }
        }

    }

    @PostMapping("/crear/factura")
    public RedirectView procesarProducto(@ModelAttribute FacturaDto facturaDto) {
        if (repositorioFactura.findByNumeroFactura(facturaDto.getNumeroFactura()) == null) {
            Producto producto = repositorioProducto.findByCodProducto(facturaDto.getCodigoProducto());
            Vendedor vendedor = repositorioVendedor.findByCodVendedor(facturaDto.getCodigoVendedor());
            if (producto == null || vendedor == null) {
                return new RedirectView("/facturasA");
            }
            Factura factura = new Factura();
            factura.setNumeroFactura(facturaDto.getNumeroFactura());
            factura.setProducto(producto);
            factura.setVendedor(vendedor);
            factura.setFechaVenta(facturaDto.getFechaVenta());
            factura.setValorFactura(facturaDto.getValorFactura());

            if (facturaDto.getNumeroFactura() == null
                    || facturaDto.getNumeroFactura() == ""
                    || facturaDto.getCodigoVendedor() == ""
                    || facturaDto.getValorFactura() == 0
                    || facturaDto.getCodigoProducto() == ""
                    || facturaDto.getCodigoProducto() == null
                    || facturaDto.getCodigoVendedor() == null
                    || facturaDto.getValorFactura() == null) {
                return new RedirectView("/crear/factura/", true);
            } else {
                Factura facturaGuardada = repositorioFactura.save(factura);
                if (facturaGuardada == null) {
                    return new RedirectView("/crear/factura/", true);
                }
                return new RedirectView("/facturas/" + facturaGuardada.getNumeroFactura(), true);
            }
        } else {
            return new RedirectView("/crear/factura/", true);
        }

    }

    @GetMapping("/facturas/Borrar") //path del controlador
    public RedirectView findFacturab(Model model) {
        model.addAttribute("factura", new FacturaDto());
        return new RedirectView("/facturasA", true);
    }

    @PostMapping("/facturas/Borrar") //path del controlador
    public String borrarFacturaV(@ModelAttribute FacturaDto factura, Model model) {

        Factura factura2 = repositorioFactura.findByNumeroFactura(factura.getNumeroFactura());

        if (factura2 == null) {
            return "redirect:/facturas/Borrar";
        } else {
            repositorioFactura.delete(factura2);
            return "redirect:/facturas";
        }

    }

    @GetMapping("/facturas/Borrar/{numeroFactura}") //path del controlador
    public String borrarFactura(@PathVariable String numeroFactura, Model model) {
        Factura factura = repositorioFactura.findByNumeroFactura(numeroFactura);
        System.out.println(factura);
        if (factura == null) {
            return "vistaFormularioAdminFacturas";
        } else {
            repositorioFactura.delete(factura);
            return "redirect:/facturas";
        }
    }

    @GetMapping("/facturas/Modificar") //path del controlador
    public String findFacturaA(Model model) {
        model.addAttribute("factura", new FacturaDto());
        return "vistaFormularioAdminFacturas";
    }

    @PostMapping("/facturas/Modificar") //path del controlador
    public String actualizarFactura(@ModelAttribute FacturaDto facturaDto, Model model) {

        Factura factura = repositorioFactura.findByNumeroFactura(facturaDto.getNumeroFactura());

        if (factura == null) {
            System.out.println("Factura no Existe");
        } else {
            Producto producto = repositorioProducto.findByCodProducto(facturaDto.getCodigoProducto());
            if (producto != null) {
                System.out.println("Producto Existe");
                //si el producto existe en la base de datos se actualiza la factura
                factura.setProducto(producto);
                System.out.println(facturaDto);
                if (facturaDto.getValorFactura() == null) {
                    factura.setValorFactura((float) 0);
                } else {
                    factura.setValorFactura(facturaDto.getValorFactura());
                }
                //se guarda la factura en la base de datos
                repositorioFactura.save(factura);
            } else {
                System.out.println("Producto no Existe");
            }

            return "redirect:/facturas/" + factura.getNumeroFactura();
        }

        return "redirect:/facturas/";

    }

//Controlador vistas Vendedor
    @GetMapping("/facturasV") //path del controlador
    public String getTodasLasFacturasV(Model model) {
        Iterable<Factura> facturas = repositorioFactura.findAll();
        Iterable<Login> login = repositorioLogin.findAll();
        ArrayList<String> cod = new ArrayList();
        ArrayList<String> nom = new ArrayList();
        for (Login i : login) {
            cod.add(i.getRolUser());
            nom.add(i.getNombreUser());
        }
        Object[] codigo = cod.toArray();
        Object[] nombre = nom.toArray();
        model.addAttribute("nombre", nombre[nombre.length - 1]);
        model.addAttribute("rol", codigo[codigo.length - 1]);
        model.addAttribute("factura", new FacturaDto());
        model.addAttribute("facturas", facturas);
        return "vistaFacturaV";
    }

    @GetMapping("/crear/facturaV") //path del controlador
    public String crearFacturaV(Model model) {
        model.addAttribute("factura", new FacturaDto());
        return "vistaFacturaV";
    }

    @PostMapping("/crear/facturaV")
    public RedirectView procesarProductoV(@ModelAttribute FacturaDto facturaDto) {
        if (repositorioFactura.findByNumeroFactura(facturaDto.getNumeroFactura()) == null) {
            Producto producto = repositorioProducto.findByCodProducto(facturaDto.getCodigoProducto());
            Vendedor vendedor = repositorioVendedor.findByCodVendedor(facturaDto.getCodigoVendedor());
            if (producto == null || vendedor == null) {
                return new RedirectView("/facturasV");
            }
            Factura factura = new Factura();
            factura.setNumeroFactura(facturaDto.getNumeroFactura());
            factura.setProducto(producto);
            factura.setVendedor(vendedor);
            factura.setFechaVenta(facturaDto.getFechaVenta());
            factura.setValorFactura(facturaDto.getValorFactura());

            if (facturaDto.getNumeroFactura() == null
                    || facturaDto.getNumeroFactura() == ""
                    || facturaDto.getCodigoVendedor() == ""
                    || facturaDto.getValorFactura() == 0
                    || facturaDto.getCodigoProducto() == ""
                    || facturaDto.getCodigoProducto() == null
                    || facturaDto.getCodigoVendedor() == null
                    || facturaDto.getValorFactura() == null) {
                return new RedirectView("/crear/facturaV", true);
            } else {
                Factura facturaGuardada = repositorioFactura.save(factura);
                if (facturaGuardada == null) {
                    return new RedirectView("/crear/facturaV", true);
                }
                return new RedirectView("/facturas/" + facturaGuardada.getNumeroFactura(), true);
            }
        } else {
            return new RedirectView("/crear/facturaV", true);
        }

    }

    @GetMapping("/consultar/facturaV") //path del controlador
    public RedirectView findFacturaV(Model model) {
        model.addAttribute("factura", new FacturaDto());
        return new RedirectView("/facturasV");
    }

    @PostMapping("/consultar/facturaV") //path del controlador
    public String procesarFacturaV(@ModelAttribute FacturaDto facturaDto, Model model) {

        boolean flag = false;
        if (facturaDto.getCodigoVendedor() != null && facturaDto.getCodigoVendedor() != "") {

            Vendedor vendedor = repositorioVendedor.findByCodVendedor(facturaDto.getCodigoVendedor());

            if (vendedor != null) {

                Iterable<Factura> facturas = repositorioFactura.findAll();
                List<Factura> facturas2 = new ArrayList<>();

                for (Factura l : facturas) {

                    if (l.getVendedor().getCodVendedor().equals(vendedor.getCodVendedor())) {
                        flag = true;
                        facturas2.add(l);
                    }
                }
                if (flag == false) {
                    return "redirect:/consultar/facturaV";
                }
                model.addAttribute("facturas", facturas2);
                return "vistaFactura";
            } else {

                return "redirect:/facturas";
            }
        } else {

            System.out.println("Numero Factura");
            Factura factura = repositorioFactura.findByNumeroFactura(facturaDto.getNumeroFactura());
            if (factura != null) {
                return "redirect:/facturas/" + factura.getNumeroFactura();
            } else {
                return "redirect:/facturasV";
            }
        }

    }

}
