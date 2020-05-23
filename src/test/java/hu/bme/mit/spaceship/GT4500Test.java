package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore tp;
  private TorpedoStore ts;

  @BeforeEach
  public void init() {
    tp = Mockito.mock(TorpedoStore.class);
    ts = Mockito.mock(TorpedoStore.class);
    this.ship = new GT4500(tp, ts);
  }

  @Test
  public void fireTorpedo_Single_Success() {
    // Arrange
    when(tp.isEmpty()).thenReturn(false);
    when(ts.isEmpty()).thenReturn(true);
    when(tp.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(tp, atLeastOnce()).fire(1);

  }

  @Test
  public void fireTorpedo_All_Success() {
    // Arrange
    when(tp.isEmpty()).thenReturn(false);
    when(ts.isEmpty()).thenReturn(false);
    when(tp.fire(1)).thenReturn(true);
    when(ts.fire(1)).thenReturn(false);

    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(tp, atLeastOnce()).fire(1);
    verify(ts, atLeastOnce()).fire(1);
  }

  @Test
  public void fireTorpedo_one_success() {
    // Arrange
    when(tp.isEmpty()).thenReturn(true);
    when(ts.isEmpty()).thenReturn(false);
    when(tp.fire(1)).thenReturn(true);
    when(ts.fire(1)).thenReturn(false);

    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(tp, never()).fire(1);
    verify(ts, atLeastOnce()).fire(1);
  }

  @Test
  public void fireTorpedo_one_success2() {
    // Arrange
    when(tp.isEmpty()).thenReturn(false);
    when(ts.isEmpty()).thenReturn(true);
    when(tp.fire(1)).thenReturn(true);
    when(ts.fire(1)).thenReturn(false);

    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(tp, atLeastOnce()).fire(1);
    verify(ts, never()).fire(1);
  }


  //My testcases


  @Test
  public void fireTorpedo_no_success() {
    // Arrange
    when(tp.isEmpty()).thenReturn(true);
    when(ts.isEmpty()).thenReturn(true);

    // Act
    final boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(tp, never()).fire(1);
    verify(ts, never()).fire(1);
    assertEquals(false, result);

  }
  
  
  @Test
  public void fireTorpedo_all_success() {
    // Arrange
    when(tp.isEmpty()).thenReturn(false);
    when(ts.isEmpty()).thenReturn(false);
    when(tp.fire(1)).thenReturn(true);
    when(ts.fire(1)).thenReturn(false);
    // Act
    final boolean result2 = ship.fireTorpedo(FiringMode.ALL);

    when(tp.fire(1)).thenReturn(false);
    when(ts.fire(1)).thenReturn(true);


    final boolean result3 = ship.fireTorpedo(FiringMode.ALL);

    when(tp.fire(1)).thenReturn(true);
    when(ts.fire(1)).thenReturn(true);


    final boolean result4 = ship.fireTorpedo(FiringMode.ALL);

    when(tp.fire(1)).thenReturn(false);
    when(ts.fire(1)).thenReturn(false);
    final boolean result5 = ship.fireTorpedo(FiringMode.ALL);
   


    // Assert
    verify(tp,atLeastOnce()).fire(1);
    verify(ts, atLeastOnce()).fire(1);
    assertEquals(true, result2);
    assertEquals(true, result3);
    assertEquals(true, result4);
    assertEquals(false, result5);

  }

  @Test
  public void fireTorpedo_alternating_behaviour() {
    // Arrange
    when(tp.isEmpty()).thenReturn(false);
    when(ts.isEmpty()).thenReturn(false);
    when(tp.fire(1)).thenReturn(false);
    when(ts.fire(1)).thenReturn(false);

    // Act tp fire
    final boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Rearrange

    // Act ts fire
    final boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Rearrange

    when(tp.fire(1)).thenReturn(true);
    when(ts.fire(1)).thenReturn(true);

    // Act tp fire
    final boolean result3 = ship.fireTorpedo(FiringMode.SINGLE);

    // Rearrange

    // Act ts fire
    final boolean result4 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(tp, times(2)).fire(1);
    verify(ts, times(2)).fire(1);
    assertEquals(false, result);
    assertEquals(false, result2);
    assertEquals(true, result3);
    assertEquals(true, result4);
  }

  @Test
  public void fireTorpedo_anti_alternating_behaviour_secondary() {
    // Arrange
    when(tp.isEmpty()).thenReturn(true);
    when(ts.isEmpty()).thenReturn(false);
    when(tp.fire(1)).thenReturn(false);
    when(ts.fire(1)).thenReturn(false);

    // Act ts fire
    final boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(tp, never()).fire(1);
    verify(ts, times(1)).fire(1);
    assertEquals(false, result);

  }

  @Test
  public void fireTorpedo_anti_alternating_behaviour_primary() {
    // Arrange
    when(tp.isEmpty()).thenReturn(false);
    when(ts.isEmpty()).thenReturn(true);
    when(tp.fire(1)).thenReturn(false);
    when(ts.fire(1)).thenReturn(false);

    // Act tp fire
    final boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    final boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    //Rearrange
    when(tp.isEmpty()).thenReturn(true);

    final boolean result3 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert

    verify(tp, times(2)).fire(1);
    verify(ts, never()).fire(1);
    assertEquals(false, result2);
    assertEquals(false, result3);

  }

}
