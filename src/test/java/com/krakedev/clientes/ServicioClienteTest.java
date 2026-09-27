package com.krakedev.clientes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.krakedev.clientes.entidades.Cliente;
import com.krakedev.clientes.services.ServicioCliente;

public class ServicioClienteTest {

	private ServicioCliente servicio;

	@BeforeEach
	void setUp() {
		servicio = new ServicioCliente();
	}

	@Test
	void crearClienteNuevoDeberiaAgregarlo() {
		Cliente cliente = new Cliente("123", "Juan", "Perez", "juan.perez@mail.com");

		Cliente resultado = servicio.crear(cliente);

		assertNotNull(resultado);
		assertEquals(1, servicio.listar().size());
		assertEquals("juan.perez@mail.com", resultado.getEmail());
	}

	@Test
	void crearClienteConCedulaExistenteDeberiaRetornarNull() {
		Cliente cliente1 = new Cliente("123", "Juan", "Perez", "juan.perez@mail.com");
		Cliente cliente2 = new Cliente("123", "Pedro", "Gomez", "pedro.gomez@mail.com");
		servicio.crear(cliente1);

		Cliente resultado = servicio.crear(cliente2);

		assertNull(resultado);
		assertEquals(1, servicio.listar().size());
	}

	@Test
	void buscarPorCedulaExistenteDeberiaRetornarCliente() {
		Cliente cliente = new Cliente("123", "Juan", "Perez", "juan.perez@mail.com");
		servicio.crear(cliente);

		Cliente resultado = servicio.buscarPorCedula("123");

		assertNotNull(resultado);
		assertEquals("Juan", resultado.getNombre());
		assertEquals("juan.perez@mail.com", resultado.getEmail());
	}

	@Test
	void buscarPorCedulaInexistenteDeberiaRetornarNull() {
		Cliente resultado = servicio.buscarPorCedula("999");

		assertNull(resultado);
	}

	@Test
	void listarDeberiaRetornarTodosLosClientes() {
		servicio.crear(new Cliente("123", "Juan", "Perez", "juan.perez@mail.com"));
		servicio.crear(new Cliente("456", "Ana", "Lopez", "ana.lopez@mail.com"));

		assertEquals(2, servicio.listar().size());
	}

	@Test
	void actualizarClienteExistenteDeberiaModificarNombreApellidoYEmail() {
		servicio.crear(new Cliente("123", "Juan", "Perez", "juan.perez@mail.com"));
		Cliente actualizado = new Cliente("123", "Carlos", "Ramirez", "carlos.ramirez@mail.com");

		Cliente resultado = servicio.actualizar("123", actualizado);

		assertNotNull(resultado);
		assertEquals("Carlos", resultado.getNombre());
		assertEquals("Ramirez", resultado.getApellido());
		assertEquals("carlos.ramirez@mail.com", resultado.getEmail());
	}

	@Test
	void actualizarClienteDeberiaPersistirNuevoEmail() {
		servicio.crear(new Cliente("123", "Juan", "Perez", "juan.perez@mail.com"));

		servicio.actualizar("123", new Cliente("123", "Juan", "Perez", "nuevo.email@mail.com"));

		assertEquals("nuevo.email@mail.com", servicio.buscarPorCedula("123").getEmail());
	}

	@Test
	void actualizarClienteInexistenteDeberiaRetornarNull() {
		Cliente actualizado = new Cliente("999", "Carlos", "Ramirez", "carlos.ramirez@mail.com");

		Cliente resultado = servicio.actualizar("999", actualizado);

		assertNull(resultado);
	}

	@Test
	void eliminarClienteExistenteDeberiaRetornarTrue() {
		servicio.crear(new Cliente("123", "Juan", "Perez", "juan.perez@mail.com"));

		boolean resultado = servicio.eliminar("123");

		assertTrue(resultado);
		assertEquals(0, servicio.listar().size());
	}

	@Test
	void eliminarClienteInexistenteDeberiaRetornarFalse() {
		boolean resultado = servicio.eliminar("999");

		assertFalse(resultado);
	}
}