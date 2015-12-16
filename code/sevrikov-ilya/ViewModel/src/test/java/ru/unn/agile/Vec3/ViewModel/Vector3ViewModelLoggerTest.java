package ru.unn.agile.Vec3.ViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class Vector3ViewModelLoggerTest {
    private Vector3ViewModel viewModel;

    public void setViewModel(final Vector3ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void initialize() {
        viewModel = new Vector3ViewModel(new Vector3FakeLogger());
    }

    @After
    public void shutdown() {
        viewModel = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void viewModelConstructorThrowExceptionWithNullLogger() {
        new Vector3ViewModel(null);
    }

    @Test
    public void canCreateViewModelWithFakeLogger() {
        assertNotNull(viewModel);
    }

    @Test
    public void viewModelHasEmptyLogAfterConstruction() {
        assertTrue(viewModel.getLog().isEmpty());
    }
}
